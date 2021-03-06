/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.fra;


import com.opengamma.financial.security.fra.FRASecurity;
import com.opengamma.financial.security.fra.ForwardRateAgreementSecurity;
import com.opengamma.sesame.Environment;
import com.opengamma.sesame.trade.ForwardRateAgreementTrade;
import com.opengamma.util.result.Result;

/**
 * Factory for creating a calculator for a FRA.
 */
public interface FRACalculatorFactory {

  /**
   * Creates the calculator for the supplied fra.
   *
   * @param env the current environment, not null
   * @param security the fra to create a calculator for, not null
   * @return result containing the calculator if successfully created, a failure result otherwise
   */
  Result<FRACalculator> createCalculator(Environment env, FRASecurity security);
  
  /**
   * Creates the calculator for the supplied fra.
   *
   * @param env the current environment, not null
   * @param security the fra to create a calculator for, not null
   * @return result containing the calculator if successfully created, a failure result otherwise
   */
  Result<FRACalculator> createCalculator(Environment env, ForwardRateAgreementSecurity security);

  /**
   * Creates the calculator for the supplied trade on a forward rate agreement.
   *
   * @param env the current environment, not null
   * @param trade on a forward rate agreement to create a calculator for, not null
   * @return result containing the calculator if successfully created, a failure result otherwise
   */
  Result<FRACalculator> createCalculator(Environment env, ForwardRateAgreementTrade trade);

}
