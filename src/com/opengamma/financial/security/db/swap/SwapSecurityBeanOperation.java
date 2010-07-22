/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 * 
 * Please see distribution for license.
 */

package com.opengamma.financial.security.db.swap;

import static com.opengamma.financial.security.db.Converters.dateToLocalDate;
import static com.opengamma.financial.security.db.Converters.localDateToDate;

import com.opengamma.financial.security.db.AbstractBeanOperation;
import com.opengamma.financial.security.db.HibernateSecurityMasterDao;
import com.opengamma.financial.security.db.OperationContext;
import com.opengamma.financial.security.swap.ForwardSwapSecurity;
import com.opengamma.financial.security.swap.SwapSecurity;
import com.opengamma.financial.security.swap.SwapSecurityVisitor;

/**
 * Bean/security conversion operations.
 */
public final class SwapSecurityBeanOperation extends AbstractBeanOperation<SwapSecurity, SwapSecurityBean> {

  /**
   * Singleton instance.
   */
  public static final SwapSecurityBeanOperation INSTANCE = new SwapSecurityBeanOperation();

  private SwapSecurityBeanOperation() {
    super("SWAP", SwapSecurity.class, SwapSecurityBean.class);
  }

  @Override
  public boolean beanEquals(final OperationContext context, SwapSecurityBean bean, SwapSecurity security) {
    // TODO
    throw new UnsupportedOperationException();
  }

  @Override
  public SwapSecurityBean createBean(final OperationContext context, final HibernateSecurityMasterDao secMasterSession, final SwapSecurity security) {
    return security.accept(new SwapSecurityVisitor<SwapSecurityBean>() {

      private SwapSecurityBean createSwapSecurityBean(final SwapSecurity security) {
        final SwapSecurityBean bean = new SwapSecurityBean();
        bean.setSwapType(SwapType.identify(security));
        bean.setTradeDate(localDateToDate(security.getTradeDate()));
        bean.setEffectiveDate(localDateToDate(security.getEffectiveDate()));
        bean.setMaturityDate(localDateToDate(security.getMaturityDate()));
        bean.setCounterparty(security.getCounterparty());
        bean.setPayLeg(SwapLegBeanOperation.createBean(secMasterSession, security.getPayLeg()));
        bean.setReceiveLeg(SwapLegBeanOperation.createBean(secMasterSession, security.getReceiveLeg()));
        return bean;
      }

      @Override
      public SwapSecurityBean visitForwardSwapSecurity(ForwardSwapSecurity security) {
        final SwapSecurityBean bean = createSwapSecurityBean(security);
        bean.setForwardStartDate(localDateToDate(security.getForwardStartDate()));
        return bean;
      }

      @Override
      public SwapSecurityBean visitSwapSecurity(SwapSecurity security) {
        return createSwapSecurityBean(security);
      }

    });
  }

  @Override
  public SwapSecurity createSecurity(final OperationContext context, final SwapSecurityBean bean) {
    return bean.getSwapType().accept(new SwapSecurityVisitor<SwapSecurity>() {
      
      @Override
      public SwapSecurity visitForwardSwapSecurity(ForwardSwapSecurity ignore) {
        return new ForwardSwapSecurity(dateToLocalDate(bean.getTradeDate()).atMidnight(), dateToLocalDate(bean.getEffectiveDate()).atMidnight(), dateToLocalDate(bean.getMaturityDate()).atMidnight(),
            bean.getCounterparty(), SwapLegBeanOperation.createSwapLeg(context.getRegionRepository(), bean.getPayLeg()), SwapLegBeanOperation.createSwapLeg(context.getRegionRepository(), bean
                .getReceiveLeg()), dateToLocalDate(bean.getForwardStartDate()).atMidnight());
      }

      @Override
      public SwapSecurity visitSwapSecurity(SwapSecurity ignore) {
        return new SwapSecurity(dateToLocalDate(bean.getTradeDate()).atMidnight(), dateToLocalDate(bean.getEffectiveDate()).atMidnight(),
            dateToLocalDate(bean.getMaturityDate()).atMidnight(), bean.getCounterparty(), SwapLegBeanOperation.createSwapLeg(context.getRegionRepository(), bean.getPayLeg()), SwapLegBeanOperation
                .createSwapLeg(context.getRegionRepository(), bean.getReceiveLeg()));
      }

    });
  }

}
