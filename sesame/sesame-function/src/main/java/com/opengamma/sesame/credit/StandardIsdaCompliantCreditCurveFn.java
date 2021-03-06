/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.sesame.credit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.threeten.bp.LocalDate;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.analytics.financial.credit.isdastandardmodel.AccrualOnDefaultFormulae;
import com.opengamma.analytics.financial.credit.isdastandardmodel.CDSAnalytic;
import com.opengamma.analytics.financial.credit.isdastandardmodel.CDSAnalyticFactory;
import com.opengamma.analytics.financial.credit.isdastandardmodel.CDSQuoteConvention;
import com.opengamma.analytics.financial.credit.isdastandardmodel.FastCreditCurveBuilder;
import com.opengamma.analytics.financial.credit.isdastandardmodel.ISDACompliantCreditCurve;
import com.opengamma.analytics.financial.credit.isdastandardmodel.ISDACompliantCreditCurveBuilder;
import com.opengamma.analytics.financial.credit.isdastandardmodel.ISDACompliantCreditCurveBuilder.ArbitrageHandling;
import com.opengamma.analytics.financial.credit.isdastandardmodel.ISDACompliantYieldCurve;
import com.opengamma.core.holiday.HolidaySource;
import com.opengamma.core.region.RegionSource;
import com.opengamma.financial.analytics.conversion.CalendarUtils;
import com.opengamma.financial.analytics.isda.credit.CdsQuote;
import com.opengamma.financial.analytics.isda.credit.CreditCurveData;
import com.opengamma.financial.analytics.isda.credit.CreditCurveDataKey;
import com.opengamma.financial.convention.IsdaCreditCurveConvention;
import com.opengamma.financial.convention.calendar.Calendar;
import com.opengamma.financial.convention.calendar.MondayToFridayCalendar;
import com.opengamma.id.ExternalId;
import com.opengamma.sesame.Environment;
import com.opengamma.sesame.credit.curve.CreditCurveDataProviderFn;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.result.Result;
import com.opengamma.util.time.Tenor;
import com.opengamma.util.tuple.ObjectsPair;
import com.opengamma.util.tuple.Pair;

/**
 * Builds ISDA compliant credit curves using standard cds contracts to define the curve instruments.
 */
public class StandardIsdaCompliantCreditCurveFn implements IsdaCompliantCreditCurveFn {
  
  private static final ISDACompliantCreditCurveBuilder CREDIT_CURVE_BUILDER = 
      new FastCreditCurveBuilder(AccrualOnDefaultFormulae.OrignalISDA, ArbitrageHandling.Fail);
  
  private final IsdaCompliantYieldCurveFn _yieldCurveFn;
  
  private final CreditCurveDataProviderFn _curveDataProviderFn;

  private final HolidaySource _holidaySource;

  private final RegionSource _regionSource;

  /**
   * Constructor for function.
   * @param yieldCurveFn function for sourcing yield curves
   * @param curveDataProviderFn function for sourcing credit curve data
   * @param holidaySource holiday source for sourcing calendars
   * @param regionSource region source for sourcing calendars
   */
  public StandardIsdaCompliantCreditCurveFn(IsdaCompliantYieldCurveFn yieldCurveFn,
                                            CreditCurveDataProviderFn curveDataProviderFn, 
                                            HolidaySource holidaySource, 
                                            RegionSource regionSource) {
    _yieldCurveFn = ArgumentChecker.notNull(yieldCurveFn, "yieldCurveFn");
    _curveDataProviderFn = ArgumentChecker.notNull(curveDataProviderFn, "curveDataProviderFn");
    _holidaySource = ArgumentChecker.notNull(holidaySource, "holidaySource");
    _regionSource = ArgumentChecker.notNull(regionSource, "regionSource");
  }

  @Override
  public Result<IsdaCreditCurve> buildIsdaCompliantCreditCurve(Environment env, 
                                                               CreditCurveDataKey creditCurveKey) {
    
    Result<IsdaYieldCurve> yieldCurveResult = 
        _yieldCurveFn.buildIsdaCompliantCurve(env, creditCurveKey.getCurrency());
    
    Result<CreditCurveData> creditCurveDataResult = 
        _curveDataProviderFn.retrieveCreditCurveData(env, creditCurveKey);
    
    if (Result.anyFailures(creditCurveDataResult, yieldCurveResult)) {
      return Result.failure(creditCurveDataResult, yieldCurveResult);
    }
    
    IsdaYieldCurve yieldCurve = yieldCurveResult.getValue();
    Pair<ISDACompliantCreditCurve, List<CDSAnalytic>> curveData =
        buildWithResolvedData(env,
                              yieldCurve.getCalibratedCurve(),
                              creditCurveDataResult.getValue(),
                              creditCurveKey);
    
    return Result.success(IsdaCreditCurve.builder()
                                         .calibratedCurve(curveData.getFirst())
                                         .curveData(creditCurveDataResult.getValue())
                                         .yieldCurve(yieldCurve)
                                         .calibratedCds(curveData.getSecond())
                                         .build());
  }

  private Pair<ISDACompliantCreditCurve, List<CDSAnalytic>> buildWithResolvedData(Environment env,
                                                                                  ISDACompliantYieldCurve yieldCurve,
                                                                                  CreditCurveData creditCurveData,
                                                                                  CreditCurveDataKey creditCurveKey) {
    
    IsdaCreditCurveConvention convention = creditCurveData.getCurveConventionLink().resolve();
    
    SortedMap<Tenor, CdsQuote> spreadData = creditCurveData.getCdsQuotes();
    
    List<CDSAnalytic> calibrationCdsList = new ArrayList<>(spreadData.size());
    List<CDSQuoteConvention> quoteList = new ArrayList<>(spreadData.size());
    
    ExternalId regionId = convention.getRegionId();
    Calendar calendar = getRegionCalendar(regionId);
    
    CDSAnalyticFactory cdsFactory = new CDSAnalyticFactory()
                                          .with(convention.getBusinessDayConvention())
                                          .with(calendar)
                                          .with(convention.getCouponInterval())
                                          .with(convention.getStubType())
                                          .withAccrualDCC(convention.getAccrualDayCount())
                                          .withCashSettle(convention.getCashSettle())
                                          .withCurveDCC(convention.getCurveDayCount())
                                          .withPayAccOnDefault(convention.isPayAccOnDefault())
                                          .withProtectionStart(convention.isProtectFromStartOfDay())
                                          .withRecoveryRate(creditCurveData.getRecoveryRate())
                                          .withStepIn(convention.getStepIn());
    
    LocalDate valuationDate = env.getValuationDate();
    
    for (Map.Entry<Tenor, CdsQuote> spreadEntry : spreadData.entrySet()) {
      CDSAnalytic cdsAnalytic;

      switch (creditCurveKey.getCdsType()) {
        case INDEX:
          cdsAnalytic = cdsFactory.makeCDX(valuationDate, spreadEntry.getKey().getPeriod());
          break;
        case SINGLE_NAME:
          cdsAnalytic = cdsFactory.makeIMMCDS(valuationDate, spreadEntry.getKey().getPeriod());
          break;
        default:
          throw new OpenGammaRuntimeException("Credit curve key with CDS type " + creditCurveKey.getCdsType() + " is " +
                                                  "invalid. Change to INDEX or SINGLE_NAME.");
      }

      CDSQuoteConvention quoteConvention = spreadEntry.getValue().toQuoteConvention();
      
      calibrationCdsList.add(cdsAnalytic);
      quoteList.add(quoteConvention);
    }

    ISDACompliantCreditCurve curve =  CREDIT_CURVE_BUILDER.calibrateCreditCurve(
        calibrationCdsList.toArray(new CDSAnalytic[calibrationCdsList.size()]),
        quoteList.toArray(new CDSQuoteConvention[quoteList.size()]),
        yieldCurve);

    return ObjectsPair.of(curve, calibrationCdsList);
    
  }

  private Calendar getRegionCalendar(ExternalId regionId) {
    if (regionId == null) {
      return new MondayToFridayCalendar("weekday calendar");
    } else {
      return CalendarUtils.getCalendar(_regionSource, _holidaySource, regionId);
    }
  }

}
