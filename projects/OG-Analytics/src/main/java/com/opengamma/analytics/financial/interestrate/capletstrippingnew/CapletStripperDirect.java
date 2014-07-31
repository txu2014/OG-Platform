/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.interestrate.capletstrippingnew;

import java.util.Arrays;

import com.opengamma.analytics.math.interpolation.PenaltyMatrixGenerator;
import com.opengamma.analytics.math.matrix.DoubleMatrix1D;
import com.opengamma.analytics.math.matrix.DoubleMatrix2D;
import com.opengamma.util.ArgumentChecker;

/**
 * Fit directly for caplet volatilities using a (penalty) matrix which imposes a penalty for curvature of the volatility 
 * 'surface' 
 */
public class CapletStripperDirect implements CapletStripper {

  private final MultiCapFloorPricer _pricer;
  private final double _lambdaT;
  private final double _lambdaK;

  /**
   * Set up the stripper 
  * @param pricer The pricer
   * @param lambda Use the same curvature penalty in both strike and expiry directions. A lower value will impose less 
   * constraint, allowing better recovery of cap values, but a less smooth volatility surface   
   */
  public CapletStripperDirect(final MultiCapFloorPricer pricer, final double lambda) {
    ArgumentChecker.notNull(pricer, "pricer");
    ArgumentChecker.notNegative(lambda, "lambda");
    _pricer = pricer;
    _lambdaT = lambda;
    _lambdaK = lambda;
  }

  /**
   *  Set up the stripper 
   * @param pricer The pricer
   * @param lambdaT the curvature penalty in the expiry direction 
   * @param lambdaK the curvature penalty in the expiry direction 
   */
  public CapletStripperDirect(final MultiCapFloorPricer pricer, final double lambdaT, final double lambdaK) {
    ArgumentChecker.notNull(pricer, "pricer");
    ArgumentChecker.notNegative(lambdaT, "lambdaT");
    ArgumentChecker.notNegative(lambdaK, "lambdaK");
    _pricer = pricer;
    _lambdaT = lambdaT;
    _lambdaK = lambdaK;
  }

  @Override
  public CapletStrippingResult solve(final double[] marketValues, final MarketDataType type) {
    ArgumentChecker.notNull(marketValues, "marketValues");
    final int n = marketValues.length;
    final double[] errors = new double[n];
    Arrays.fill(errors, 1.0);
    final DoubleMatrix1D guess = new DoubleMatrix1D(_pricer.getNumCaplets(), 0.4);
    return solve(marketValues, type, errors, guess);
  }

  @Override
  public CapletStrippingResult solve(final double[] marketValues, final MarketDataType type, final double[] errors) {
    final DoubleMatrix1D guess = new DoubleMatrix1D(_pricer.getNumCaplets(), 0.4);
    return solve(marketValues, type, errors, guess);
  }

  @Override
  public CapletStrippingResult solve(final double[] marketValues, final MarketDataType type, final DoubleMatrix1D guess) {
    ArgumentChecker.notNull(marketValues, "marketValues");
    final int n = marketValues.length;
    final double[] errors = new double[n];
    Arrays.fill(errors, 1.0);
    return solve(marketValues, type, errors, guess);
  }

  @Override
  public CapletStrippingResult solve(final double[] marketValues, final MarketDataType type, final double[] errors, final DoubleMatrix1D guess) {
    final DoubleMatrix2D p = getPenaltyMatrix(_pricer.getStrikes(), _pricer.getCapletExpiries(), _lambdaK, _lambdaT);
    final CapletStrippingImp imp = getImp(marketValues);
    if (type == MarketDataType.PRICE) {
      return imp.solveForCapPrices(marketValues, errors, guess, p);
    } else if (type == MarketDataType.VOL) {
      return imp.solveForCapVols(marketValues, errors, guess, p);
    }
    throw new IllegalArgumentException("Unknown MarketDataType " + type.toString());
  }

  private CapletStrippingImp getImp(final double[] values) {

    ArgumentChecker.notEmpty(values, "values");
    final int nCaps = _pricer.getNumCaps();
    ArgumentChecker.isTrue(nCaps == values.length, "Expected {} cap prices, but only given {}", nCaps, values.length);
    final DiscreteVolatilityFunctionProvider volPro = new DiscreteVolatilityFunctionProviderDirect(_pricer.getNumCaplets());
    return new CapletStrippingImp(_pricer, volPro);
  }

  protected DoubleMatrix2D getPenaltyMatrix(final double[] strikes, final double[] expries, final double lambdaK, final double lambdaT) {

    //use second order difference unless too few points  
    final int diffOrderK = Math.min(2, strikes.length - 1);
    final int diffOrderT = Math.min(2, expries.length - 1);
    final double effLambdaK = diffOrderK == 0 ? 0.0 : lambdaK;
    final double effLambdaT = diffOrderT == 0 ? 0.0 : lambdaT;
    return PenaltyMatrixGenerator.getPenaltyMatrix(new double[][] {strikes, expries }, new int[] {diffOrderK, diffOrderT }, new double[] {effLambdaK, effLambdaT });
  }

}