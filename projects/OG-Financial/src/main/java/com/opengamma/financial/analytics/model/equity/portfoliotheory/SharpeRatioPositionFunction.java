/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.model.equity.portfoliotheory;

import com.opengamma.engine.target.ComputationTargetType;

/**
 * 
 */
public class SharpeRatioPositionFunction extends SharpeRatioFunction {

  public SharpeRatioPositionFunction(final String resolutionKey) {
    super(resolutionKey);
  }

  @Override
  public ComputationTargetType getTargetType() {
    return ComputationTargetType.POSITION;
  }

}
