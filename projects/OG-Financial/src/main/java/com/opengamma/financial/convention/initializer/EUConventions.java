/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.convention.initializer;

import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.DEPOSIT;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.DEPOSIT_ON;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.FIXED_LEG;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.FX_FORWARD;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.FX_SPOT;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.IBOR_LEG;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.IRS_IBOR_LEG;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.LIBOR;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.OIS_ON_LEG;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.OVERNIGHT;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.PAY_LAG;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.QUARTERLY;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.SCHEME_NAME;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.SERIAL;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.STIR_FUTURES;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.TENOR_STR_12M;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.TENOR_STR_1M;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.TENOR_STR_1Y;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.TENOR_STR_3M;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.TENOR_STR_6M;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.getConventionName;
import static com.opengamma.financial.convention.initializer.PerCurrencyConventionHelper.getIds;

import org.threeten.bp.LocalTime;

import com.opengamma.analytics.math.interpolation.Interpolator1DFactory;
import com.opengamma.core.id.ExternalSchemes;
import com.opengamma.financial.convention.DepositConvention;
import com.opengamma.financial.convention.FXForwardAndSwapConvention;
import com.opengamma.financial.convention.FXSpotConvention;
import com.opengamma.financial.convention.IborIndexConvention;
import com.opengamma.financial.convention.InterestRateFutureConvention;
import com.opengamma.financial.convention.OISLegConvention;
import com.opengamma.financial.convention.OvernightIndexConvention;
import com.opengamma.financial.convention.StubType;
import com.opengamma.financial.convention.SwapFixedLegConvention;
import com.opengamma.financial.convention.VanillaIborLegConvention;
import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.businessday.BusinessDayConventionFactory;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.daycount.DayCountFactory;
import com.opengamma.financial.convention.expirycalc.ExchangeTradedInstrumentExpiryCalculator;
import com.opengamma.financial.convention.expirycalc.IMMFutureAndFutureOptionMonthlyExpiryCalculator;
import com.opengamma.financial.convention.expirycalc.IMMFutureAndFutureOptionQuarterlyExpiryCalculator;
import com.opengamma.id.ExternalId;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.master.convention.ConventionMaster;
import com.opengamma.util.money.Currency;
import com.opengamma.util.time.Tenor;

/**
 * The conventions for Europe.
 */
public class EUConventions extends ConventionMasterInitializer {

  /** Singleton. */
  public static final ConventionMasterInitializer INSTANCE = new EUConventions();
  /** OIS X-Ccy USD/EUR ON leg convention string **/
  public static final String OIS_USD_EUR_ON_LEG = "EUR Overnight USD/EUR XCcy Leg";
  /** The Euribor string **/
  public static final String EURIBOR = "Euribor";
  /** The IRS Euribor leg string **/
  public static final String EURIBOR_LEG = EURIBOR + " Leg";

  private static final BusinessDayConvention MODIFIED_FOLLOWING = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Modified Following");
  private static final BusinessDayConvention FOLLOWING = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Following");
  private static final DayCount ACT_360 = DayCountFactory.INSTANCE.getDayCount("Actual/360");
  private static final DayCount THIRTY_U_360 = DayCountFactory.INSTANCE.getDayCount("30U/360");  
  private static final ExternalId EU = ExternalSchemes.financialRegionId("EU");
  private static final ExternalId USEU = ExternalSchemes.financialRegionId("US+EU");

  /**
   * Restricted constructor.
   */
  protected EUConventions() {
  }

  //-------------------------------------------------------------------------
  @Override
  public void init(final ConventionMaster master) {
    // Index (Overnight and Ibor-like)
    final String onIndexName = getConventionName(Currency.EUR, OVERNIGHT);
    final ExternalId onIndexId = ExternalId.of(SCHEME_NAME, onIndexName);
    final OvernightIndexConvention onIndex = new OvernightIndexConvention(
        onIndexName, getIds(Currency.EUR, OVERNIGHT), ACT_360, 0, Currency.EUR, EU);
    final String liborConventionName = getConventionName(Currency.EUR, LIBOR);
    final IborIndexConvention liborIndex = new IborIndexConvention(
        liborConventionName, getIds(Currency.EUR, LIBOR), ACT_360, MODIFIED_FOLLOWING, 2, true, Currency.EUR,
        LocalTime.of(11, 00), "EU", EU, EU, "");
    final ExternalId liborConventionId = ExternalId.of(SCHEME_NAME, liborConventionName);
    final String euriborConventionName = getConventionName(Currency.EUR, EURIBOR);
    final IborIndexConvention euriborIndex = new IborIndexConvention(
        euriborConventionName, getIds(Currency.EUR, EURIBOR), ACT_360, MODIFIED_FOLLOWING, 2, true, Currency.EUR,
        LocalTime.of(11, 00), "EU", EU, EU, "");
    final ExternalId euriborConventionId = ExternalId.of(SCHEME_NAME, euriborConventionName);
    // Deposit
    final String depositONConventionName = getConventionName(Currency.EUR, DEPOSIT_ON);
    final DepositConvention depositONConvention = new DepositConvention(
        depositONConventionName, getIds(Currency.EUR, DEPOSIT_ON), ACT_360, FOLLOWING, 0, false, Currency.EUR, EU);
    final String depositConventionName = getConventionName(Currency.EUR, DEPOSIT);
    final DepositConvention depositConvention = new DepositConvention(
        depositConventionName, getIds(Currency.EUR, DEPOSIT), ACT_360, FOLLOWING, 2, false, Currency.EUR, EU);
    // Fixed legs
    final String oisFixedLegConventionName = getConventionName(Currency.EUR, TENOR_STR_1Y, PAY_LAG + FIXED_LEG);
    final SwapFixedLegConvention oisFixedLegConvention = new SwapFixedLegConvention(
        oisFixedLegConventionName, getIds(Currency.EUR, TENOR_STR_1Y, PAY_LAG + FIXED_LEG),
        Tenor.ONE_YEAR, ACT_360, MODIFIED_FOLLOWING, Currency.EUR, EU, 2, true, StubType.SHORT_START, false, 2);
    final String irsFixedLegConventionName = getConventionName(Currency.EUR, TENOR_STR_1Y, FIXED_LEG);
    final SwapFixedLegConvention irsFixedLegConvention = new SwapFixedLegConvention(
        irsFixedLegConventionName, getIds(Currency.EUR, TENOR_STR_1Y, FIXED_LEG),
        Tenor.ONE_YEAR, THIRTY_U_360, MODIFIED_FOLLOWING, Currency.EUR, EU, 2, true, StubType.SHORT_START, false, 0);
    // OIS legs
    final String oisFloatLegConventionName = getConventionName(Currency.EUR, OIS_ON_LEG);
    final OISLegConvention oisFloatLegConvention = new OISLegConvention(
        oisFloatLegConventionName, getIds(Currency.EUR, OIS_ON_LEG), onIndexId,
        Tenor.ONE_YEAR, MODIFIED_FOLLOWING, 2, true, StubType.SHORT_START, false, 2);
    // Ibor legs
    final String irsLibor6MLegConventionName = getConventionName(Currency.EUR, TENOR_STR_6M, IBOR_LEG);
    final VanillaIborLegConvention irsLibor6MLegConvention = new VanillaIborLegConvention(
        irsLibor6MLegConventionName, getIds(Currency.EUR, TENOR_STR_6M, IBOR_LEG),
        liborConventionId, true, Interpolator1DFactory.LINEAR, Tenor.SIX_MONTHS, 2, true, StubType.NONE, false, 0);
    
    final String irsEuribor12MLegConventionName = getConventionName(Currency.EUR, TENOR_STR_12M, EURIBOR_LEG);
    final VanillaIborLegConvention irsEuribor12MLegConvention = new VanillaIborLegConvention(
        irsEuribor12MLegConventionName, getIds(Currency.EUR, TENOR_STR_12M, EURIBOR_LEG),
        euriborConventionId, true, Interpolator1DFactory.LINEAR, Tenor.TWELVE_MONTHS, 2, true, StubType.SHORT_START, false, 0);
    
    final String irsEuribor6MLegConventionName = getConventionName(Currency.EUR, TENOR_STR_6M, EURIBOR_LEG);
    final VanillaIborLegConvention irsEuribor6MLegConvention = new VanillaIborLegConvention(
        irsEuribor6MLegConventionName, getIds(Currency.EUR, TENOR_STR_6M, EURIBOR_LEG),
        euriborConventionId, true, Interpolator1DFactory.LINEAR, Tenor.SIX_MONTHS, 2, true, StubType.SHORT_START, false, 0);
    
    final String irsEuribor3MLegConventionName = getConventionName(Currency.EUR, TENOR_STR_3M, EURIBOR_LEG);
    final VanillaIborLegConvention irsEuribor3MLegConvention = new VanillaIborLegConvention(
        irsEuribor3MLegConventionName, getIds(Currency.EUR, TENOR_STR_3M, EURIBOR_LEG),
        euriborConventionId, true, Interpolator1DFactory.LINEAR, Tenor.THREE_MONTHS, 2, true, StubType.SHORT_START, false, 0);
    
    final String irsEuribor1MLegConventionName = getConventionName(Currency.EUR, TENOR_STR_1M, EURIBOR_LEG);
    final VanillaIborLegConvention irsEuribor1MLegConvention = new VanillaIborLegConvention(
        irsEuribor1MLegConventionName, getIds(Currency.EUR, TENOR_STR_1M, EURIBOR_LEG),
        euriborConventionId, true, Interpolator1DFactory.LINEAR, Tenor.ONE_MONTH, 2, true, StubType.SHORT_START, false, 0);
    
    // Note: Temporally used to retrieve underlying index convention.
    final String irsibor12MLegConventionName = getConventionName(Currency.EUR, TENOR_STR_12M, IRS_IBOR_LEG);
    final VanillaIborLegConvention irsIbor12MLegConvention = new VanillaIborLegConvention(
        irsibor12MLegConventionName, getIds(Currency.EUR, TENOR_STR_12M, IRS_IBOR_LEG),
        euriborConventionId, true, Interpolator1DFactory.LINEAR, Tenor.TWELVE_MONTHS, 2, true, StubType.SHORT_START, false, 0);
    
    final String irsibor6MLegConventionName = getConventionName(Currency.EUR, TENOR_STR_6M, IRS_IBOR_LEG);
    final VanillaIborLegConvention irsIbor6MLegConvention = new VanillaIborLegConvention(
        irsibor6MLegConventionName, getIds(Currency.EUR, TENOR_STR_6M, IRS_IBOR_LEG),
        euriborConventionId, true, Interpolator1DFactory.LINEAR, Tenor.SIX_MONTHS, 2, true, StubType.SHORT_START, false, 0);
    
    final String irsibor3MLegConventionName = getConventionName(Currency.EUR, TENOR_STR_3M, IRS_IBOR_LEG);
    final VanillaIborLegConvention irsIbor3MLegConvention = new VanillaIborLegConvention(
        irsibor3MLegConventionName, getIds(Currency.EUR, TENOR_STR_3M, IRS_IBOR_LEG),
        euriborConventionId, true, Interpolator1DFactory.LINEAR, Tenor.THREE_MONTHS, 2, true, StubType.SHORT_START, false, 0);
    
    final String irsibor1MLegConventionName = getConventionName(Currency.EUR, TENOR_STR_1M, IRS_IBOR_LEG);
    final VanillaIborLegConvention irsIbor1MLegConvention = new VanillaIborLegConvention(
        irsibor1MLegConventionName, getIds(Currency.EUR, TENOR_STR_1M, IRS_IBOR_LEG),
        euriborConventionId, true, Interpolator1DFactory.LINEAR, Tenor.ONE_MONTH, 2, true, StubType.SHORT_START, false, 0);
    
    // Futures
    final String quarterlySTIRFutureConventionName = getConventionName(Currency.EUR, STIR_FUTURES + QUARTERLY);    
    final InterestRateFutureConvention quarterlySTIRFutureConvention = new InterestRateFutureConvention(
        quarterlySTIRFutureConventionName, 
        ExternalIdBundle.of(ExternalId.of(SCHEME_NAME, quarterlySTIRFutureConventionName)),
        ExternalId.of(ExchangeTradedInstrumentExpiryCalculator.SCHEME, IMMFutureAndFutureOptionQuarterlyExpiryCalculator.NAME), EU, euriborConventionId);
    final String serialFutureConventionName = getConventionName(Currency.EUR, STIR_FUTURES + SERIAL);
    final InterestRateFutureConvention serialSTIRFutureConvention = new InterestRateFutureConvention(
        serialFutureConventionName, ExternalIdBundle.of(ExternalId.of(SCHEME_NAME, serialFutureConventionName)),
        ExternalId.of(ExchangeTradedInstrumentExpiryCalculator.SCHEME, IMMFutureAndFutureOptionMonthlyExpiryCalculator.NAME), EU, liborConventionId);
    
    // Forex
    final String fxSpotEURUSDName = FX_SPOT + " EUR/USD";
    final FXSpotConvention fxSpotEURUSD = new FXSpotConvention(fxSpotEURUSDName, ExternalIdBundle.of(ExternalId.of(SCHEME_NAME, fxSpotEURUSDName)), 2, USEU);
    final String fxFwdEURUSDName = FX_FORWARD + " EUR/USD";
    final FXForwardAndSwapConvention fxForwardEURUSD = new FXForwardAndSwapConvention(
        fxFwdEURUSDName, ExternalIdBundle.of(ExternalId.of(SCHEME_NAME, fxFwdEURUSDName)),
        ExternalId.of(SCHEME_NAME, fxSpotEURUSDName), FOLLOWING, false, USEU);
    
    // X-Ccy OIS
    final OISLegConvention oisXCcyUSDLegConvention = new OISLegConvention(
        OIS_USD_EUR_ON_LEG, getIds(OIS_USD_EUR_ON_LEG), onIndexId,
        Tenor.THREE_MONTHS, MODIFIED_FOLLOWING, 2, true, StubType.NONE, false, 2);
    
    // Convention add
    addConvention(master, onIndex);
    addConvention(master, liborIndex);
    addConvention(master, euriborIndex);
    addConvention(master, depositONConvention);
    addConvention(master, depositConvention);
    addConvention(master, oisFixedLegConvention);
    addConvention(master, oisFloatLegConvention);
    addConvention(master, irsFixedLegConvention);
    addConvention(master, irsLibor6MLegConvention);
    addConvention(master, irsEuribor12MLegConvention);
    addConvention(master, irsEuribor6MLegConvention);
    addConvention(master, irsEuribor3MLegConvention);
    addConvention(master, irsEuribor1MLegConvention);
    addConvention(master, irsIbor12MLegConvention);
    addConvention(master, irsIbor6MLegConvention);
    addConvention(master, irsIbor3MLegConvention);
    addConvention(master, irsIbor1MLegConvention);
    addConvention(master, quarterlySTIRFutureConvention);
    addConvention(master, serialSTIRFutureConvention);
    addConvention(master, fxSpotEURUSD);
    addConvention(master, fxForwardEURUSD);
    addConvention(master, oisXCcyUSDLegConvention);
  }

}