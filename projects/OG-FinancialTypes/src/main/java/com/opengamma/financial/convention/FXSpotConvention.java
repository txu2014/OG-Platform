/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.convention;

import java.util.Map;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.id.ExternalId;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.util.ArgumentChecker;

/**
 * Convention for FX spot.
 */
@BeanDefinition
public class FXSpotConvention extends Convention {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The number of settlement days.
   */
  @PropertyDefinition
  private int _settlementDays;
  /**
   * The settlement region.
   */
  @PropertyDefinition
  private ExternalId _settlementRegion;

  /**
   * Creates an instance.
   */
  protected FXSpotConvention() {
  }

  /**
   * Creates an instance.
   * 
   * @param name  the convention name, not null
   * @param externalIdBundle  the external identifiers for this convention, not null
   * @param settlementDays  the number of settlement days
   * @param settlementRegion  the settlement region, can be null
   */
  public FXSpotConvention(
      final String name, final ExternalIdBundle externalIdBundle, final int settlementDays,
      final ExternalId settlementRegion) {
    super(name, externalIdBundle);
    setSettlementDays(settlementDays);
    setSettlementRegion(settlementRegion);
  }

  //-------------------------------------------------------------------------
  /**
   * Accepts a visitor to manage traversal of the hierarchy.
   *
   * @param <T>  the result type of the visitor
   * @param visitor  the visitor, not null
   * @return the result
   */
  @Override
  public <T> T accept(final ConventionVisitor<T> visitor) {
    ArgumentChecker.notNull(visitor, "visitor");
    return visitor.visitFXSpotConvention(this);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code FXSpotConvention}.
   * @return the meta-bean, not null
   */
  public static FXSpotConvention.Meta meta() {
    return FXSpotConvention.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(FXSpotConvention.Meta.INSTANCE);
  }

  @Override
  public FXSpotConvention.Meta metaBean() {
    return FXSpotConvention.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the number of settlement days.
   * @return the value of the property
   */
  public int getSettlementDays() {
    return _settlementDays;
  }

  /**
   * Sets the number of settlement days.
   * @param settlementDays  the new value of the property
   */
  public void setSettlementDays(int settlementDays) {
    this._settlementDays = settlementDays;
  }

  /**
   * Gets the the {@code settlementDays} property.
   * @return the property, not null
   */
  public final Property<Integer> settlementDays() {
    return metaBean().settlementDays().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the settlement region.
   * @return the value of the property
   */
  public ExternalId getSettlementRegion() {
    return _settlementRegion;
  }

  /**
   * Sets the settlement region.
   * @param settlementRegion  the new value of the property
   */
  public void setSettlementRegion(ExternalId settlementRegion) {
    this._settlementRegion = settlementRegion;
  }

  /**
   * Gets the the {@code settlementRegion} property.
   * @return the property, not null
   */
  public final Property<ExternalId> settlementRegion() {
    return metaBean().settlementRegion().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public FXSpotConvention clone() {
    return (FXSpotConvention) super.clone();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      FXSpotConvention other = (FXSpotConvention) obj;
      return (getSettlementDays() == other.getSettlementDays()) &&
          JodaBeanUtils.equal(getSettlementRegion(), other.getSettlementRegion()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getSettlementDays());
    hash += hash * 31 + JodaBeanUtils.hashCode(getSettlementRegion());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("FXSpotConvention{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  @Override
  protected void toString(StringBuilder buf) {
    super.toString(buf);
    buf.append("settlementDays").append('=').append(JodaBeanUtils.toString(getSettlementDays())).append(',').append(' ');
    buf.append("settlementRegion").append('=').append(JodaBeanUtils.toString(getSettlementRegion())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code FXSpotConvention}.
   */
  public static class Meta extends Convention.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code settlementDays} property.
     */
    private final MetaProperty<Integer> _settlementDays = DirectMetaProperty.ofReadWrite(
        this, "settlementDays", FXSpotConvention.class, Integer.TYPE);
    /**
     * The meta-property for the {@code settlementRegion} property.
     */
    private final MetaProperty<ExternalId> _settlementRegion = DirectMetaProperty.ofReadWrite(
        this, "settlementRegion", FXSpotConvention.class, ExternalId.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "settlementDays",
        "settlementRegion");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -295948000:  // settlementDays
          return _settlementDays;
        case -534226563:  // settlementRegion
          return _settlementRegion;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends FXSpotConvention> builder() {
      return new DirectBeanBuilder<FXSpotConvention>(new FXSpotConvention());
    }

    @Override
    public Class<? extends FXSpotConvention> beanType() {
      return FXSpotConvention.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code settlementDays} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Integer> settlementDays() {
      return _settlementDays;
    }

    /**
     * The meta-property for the {@code settlementRegion} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalId> settlementRegion() {
      return _settlementRegion;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -295948000:  // settlementDays
          return ((FXSpotConvention) bean).getSettlementDays();
        case -534226563:  // settlementRegion
          return ((FXSpotConvention) bean).getSettlementRegion();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -295948000:  // settlementDays
          ((FXSpotConvention) bean).setSettlementDays((Integer) newValue);
          return;
        case -534226563:  // settlementRegion
          ((FXSpotConvention) bean).setSettlementRegion((ExternalId) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
