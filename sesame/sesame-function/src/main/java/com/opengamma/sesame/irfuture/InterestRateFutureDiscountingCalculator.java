/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.irfuture;

import java.util.Map;

import org.threeten.bp.ZonedDateTime;

import com.opengamma.analytics.financial.instrument.InstrumentDefinition;
import com.opengamma.analytics.financial.interestrate.InstrumentDerivative;
import com.opengamma.analytics.financial.interestrate.InstrumentDerivativeVisitor;
import com.opengamma.analytics.financial.interestrate.InstrumentDerivativeVisitorAdapter;
import com.opengamma.analytics.financial.interestrate.MarginPriceVisitor;
import com.opengamma.analytics.financial.provider.calculator.discounting.MarketQuoteDiscountingCalculator;
import com.opengamma.analytics.financial.provider.calculator.discounting.PV01CurveParametersCalculator;
import com.opengamma.analytics.financial.provider.calculator.discounting.ParRateDiscountingCalculator;
import com.opengamma.analytics.financial.provider.calculator.discounting.PresentValueCurveSensitivityDiscountingCalculator;
import com.opengamma.analytics.financial.provider.calculator.discounting.PresentValueDiscountingCalculator;
import com.opengamma.analytics.financial.provider.description.interestrate.MulticurveProviderInterface;
import com.opengamma.analytics.financial.provider.description.interestrate.ParameterProviderInterface;
import com.opengamma.analytics.financial.provider.sensitivity.multicurve.MultipleCurrencyMulticurveSensitivity;
import com.opengamma.analytics.financial.provider.sensitivity.multicurve.MultipleCurrencyParameterSensitivity;
import com.opengamma.analytics.financial.provider.sensitivity.parameter.ParameterSensitivityParameterCalculator;
import com.opengamma.analytics.util.amount.ReferenceAmount;
import com.opengamma.financial.analytics.conversion.FixedIncomeConverterDataProvider;
import com.opengamma.financial.analytics.conversion.InterestRateFutureTradeConverter;
import com.opengamma.financial.analytics.model.fixedincome.BucketedCurveSensitivities;
import com.opengamma.financial.analytics.timeseries.HistoricalTimeSeriesBundle;
import com.opengamma.sesame.CurveMatrixLabeller;
import com.opengamma.sesame.ZeroIRDeltaBucketingUtils;
import com.opengamma.sesame.trade.InterestRateFutureTrade;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.money.Currency;
import com.opengamma.util.money.MultipleCurrencyAmount;
import com.opengamma.util.result.Result;
import com.opengamma.util.tuple.Pair;

/**
 * Default calculator for interest rate futures.
 */
public class InterestRateFutureDiscountingCalculator implements InterestRateFutureCalculator {

  /**
   * Calculator for present value.
   */
  private static final PresentValueDiscountingCalculator PVDC = PresentValueDiscountingCalculator.getInstance();

  /**
   * Calculator for par rate.
   */
  private static final ParRateDiscountingCalculator PRDC = ParRateDiscountingCalculator.getInstance();

  /**
   * Calculator for PV01
   */
  private static final PV01CurveParametersCalculator<ParameterProviderInterface> PV01C =
      new PV01CurveParametersCalculator<>(PresentValueCurveSensitivityDiscountingCalculator.getInstance());
      
  private static final MarginPriceVisitor MPV = MarginPriceVisitor.getInstance();
  
  private static final MarketQuoteDiscountingCalculator MQDC = MarketQuoteDiscountingCalculator.getInstance();
  
  /** 
   * The present value curve sensitivity discounting calculator 
   */
  private static final InstrumentDerivativeVisitor<ParameterProviderInterface, MultipleCurrencyMulticurveSensitivity> PVCSDC =
      PresentValueCurveSensitivityDiscountingCalculator.getInstance();
  
  /** 
   * The parameter sensitivity calculator 
   * */
  private static final ParameterSensitivityParameterCalculator<ParameterProviderInterface> PSC =
      new ParameterSensitivityParameterCalculator<>(PVCSDC);

  /**
   * Derivative form of the security.
   */
  private final InstrumentDerivative _derivative;

  /**
   * The multicurve bundle.
   */
  private final MulticurveProviderInterface _bundle;

  /**
   * Map containing the per curve labellers.
   */
  private final Map<String, CurveMatrixLabeller> _curveLabellers;

  /**
   * Provides scaling to/from basis points.
   */
  private static final double BP_FACTOR = 1.0E-4;

  /**
   * Provides scaling to Futures equivalence - Analytics representations use 1 as opposed to 100.
   */
  private static final double FUTURES_EQUIV_FACTOR = 100;

  public InterestRateFutureDiscountingCalculator(InterestRateFutureTrade irFutureTrade,
                                                 MulticurveProviderInterface bundle,
                                                 Map<String, CurveMatrixLabeller> curveLabellers,
                                                 InterestRateFutureTradeConverter tradeConverter,
                                                 ZonedDateTime valuationTime,
                                                 FixedIncomeConverterDataProvider definitionToDerivativeConverter,
                                                 HistoricalTimeSeriesBundle fixings) {

    _derivative = createInstrumentDerivative(irFutureTrade, 
                                              tradeConverter, 
                                              valuationTime, 
                                              definitionToDerivativeConverter, 
                                              fixings);

    _bundle = ArgumentChecker.notNull(bundle, "bundle");
    _curveLabellers = ArgumentChecker.notNull(curveLabellers, "curveLabellers");
  }

  @Override
  public Result<MultipleCurrencyAmount> calculatePV() {
    return Result.success(calculateResult(PVDC));
  }

  @Override
  public Result<Double> calculateParRate() {
    return Result.success(calculateResult(PRDC));
  }

  @Override
  public Result<ReferenceAmount<Pair<String, Currency>>> calculatePV01() {
    return Result.success(calculateResult(PV01C).multiplyBy(FUTURES_EQUIV_FACTOR));
  }

  @Override
  public Result<Double> getSecurityMarketPrice() {
    return Result.success(_derivative.accept(MPV));
  }

  @Override
  public Result<Double> calculateSecurityModelPrice() {
    return Result.success(calculateResult(MQDC));
  }

  @Override
  public Result<BucketedCurveSensitivities> calculateBucketedZeroIRDelta() {

    MultipleCurrencyParameterSensitivity sensitivity = 
          PSC.calculateSensitivity(_derivative, _bundle)
                                  .multipliedBy(BP_FACTOR)
                                  .multipliedBy(FUTURES_EQUIV_FACTOR);

    BucketedCurveSensitivities bucketedSensitivities = 
          ZeroIRDeltaBucketingUtils.getBucketedCurveSensitivities(sensitivity, _curveLabellers);
    return Result.success(bucketedSensitivities);
  }

  private <T> T calculateResult(InstrumentDerivativeVisitorAdapter<ParameterProviderInterface, T> calculator) {
    return _derivative.accept(calculator, _bundle);
  }

  private ReferenceAmount<Pair<String, Currency>> 
  calculateResult(InstrumentDerivativeVisitor<ParameterProviderInterface, 
                  ReferenceAmount<Pair<String, Currency>>> calculator) {
    return _derivative.accept(calculator, _bundle);
  }

  private InstrumentDerivative createInstrumentDerivative(InterestRateFutureTrade irFutureTrade,
                                                          InterestRateFutureTradeConverter converter,
                                                          ZonedDateTime valuationTime,
                                                          FixedIncomeConverterDataProvider definitionToDerivativeConverter,
                                                          HistoricalTimeSeriesBundle fixings) {
    InstrumentDefinition<?> definition = converter.convert(irFutureTrade.getTrade());
    return definitionToDerivativeConverter.convert(irFutureTrade.getSecurity(), definition, valuationTime, fixings);
  }
}
