/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetTime;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.opengamma.core.link.ConfigLink;
import com.opengamma.core.position.Counterparty;
import com.opengamma.core.position.Trade;
import com.opengamma.core.position.impl.SimpleCounterparty;
import com.opengamma.core.position.impl.SimpleTrade;
import com.opengamma.core.security.SecuritySource;
import com.opengamma.financial.analytics.curve.CurveConstructionConfiguration;
import com.opengamma.financial.analytics.curve.exposure.ExposureFunction;
import com.opengamma.financial.analytics.curve.exposure.ExposureFunctionFactory;
import com.opengamma.financial.analytics.curve.exposure.ExposureFunctions;
import com.opengamma.financial.security.FinancialSecurity;
import com.opengamma.id.ExternalId;
import com.opengamma.util.ArgumentChecker;

/**
 * Responsible for taking the set of available exposure functions and determining the
 * set of curve configurations that are applicable for a particular security.
 *
 * Note that this functionality should really be provided by the ExposureFunctions config
 * object i.e. obeying tell, don't ask.
 */
public class MarketExposureSelector implements CurveSelector {

  private final SecuritySource _securitySource;
  private final List<ExposureFunction> _exposureFunctions;
  private final Map<ExternalId, String> _idsToNames;

  public MarketExposureSelector(ExposureFunctions exposureFunctions, SecuritySource securitySource) {
    _securitySource = securitySource;
    ArgumentChecker.notNull(exposureFunctions, "exposureFunctions");

    _exposureFunctions = extractFunctions(exposureFunctions.getExposureFunctions());
    _idsToNames = exposureFunctions.getIdsToNames();
  }

  private List<ExposureFunction> extractFunctions(List<String> exposureFunctions) {
    return Lists.transform(exposureFunctions, new Function<String, ExposureFunction>() {
      @Override
      public ExposureFunction apply(String name) {
        return ExposureFunctionFactory.getExposureFunction(_securitySource, name);
      }
    });
  }
  
  /**
   * Returns the set of curve configurations that are required for a given trade.
   *
   * @param trade the trade to find curve configurations for.
   * @return the set of curve configurations that are required for a given trade.
   */
  public Set<CurveConstructionConfiguration> determineCurveConfigurations(Trade trade) {
    Set<CurveConstructionConfiguration> curveConfigs = new HashSet<>();

    for (String curveName : getMulticurveNames(trade)) {
      curveConfigs.add(resolve(curveName));
    }
    return curveConfigs;
  }

  public Set<String> getMulticurveNames(Trade trade) {
    for (ExposureFunction exposureFunction : _exposureFunctions) {
      List<ExternalId> ids = exposureFunction.getIds(trade);

      if (ids != null && !ids.isEmpty()) {
        Set<String> curveNames = new HashSet<>();

        for (ExternalId id : ids) {
          String name = _idsToNames.get(id);

          if (name != null) {
            curveNames.add(name);
          } else {
            break;
          }
        }
        return curveNames;
      }
    }
    return ImmutableSet.of();
  }

  /**
   * Returns the set of curves that are required for the supplied security.
   * <p>
   * This method calls uses a trade wrapper because curve configurations are resolved using trade attributes as well as
   * security attributes. This means that this implementation <b>will not work</b> for exposure functions that resolve
   * against trade attributes because of the lack of access to the trade object itself.
   *
   * @param security the security to find curves for.
   * @return the set of names of the required curves.
   * 
   * @deprecated use {@link #determineCurveConfigurations(Trade)} which handles trade exposure functions correctly.
   */
  @Deprecated
  public Set<CurveConstructionConfiguration> findCurveConfigurationsForSecurity(FinancialSecurity security) {
    
    Trade trade = new SimpleTrade(security,
                                  BigDecimal.ONE,
                                  new SimpleCounterparty(ExternalId.of(Counterparty.DEFAULT_SCHEME, "CPARTY")),
                                  LocalDate.now(),
                                  OffsetTime.now());

    return determineCurveConfigurations(trade);
  }

  private CurveConstructionConfiguration resolve(String name) {
    return ConfigLink.resolvable(name, CurveConstructionConfiguration.class).resolve();
  }

}
