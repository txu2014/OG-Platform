/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.sesame.credit;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.financial.security.swap.InterestRateNotional;

/**
 * Captures fields from cds required for pricing.
 */
@BeanDefinition
public class CdsData implements ImmutableBean {
  
  @PropertyDefinition(validate = "notNull")
  private final InterestRateNotional _interestRateNotional;
  
  @PropertyDefinition(validate = "notNull")
  private final double _coupon;

  @PropertyDefinition(validate = "notNull")
  private final boolean _buy;

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code CdsData}.
   * @return the meta-bean, not null
   */
  public static CdsData.Meta meta() {
    return CdsData.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(CdsData.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static CdsData.Builder builder() {
    return new CdsData.Builder();
  }

  /**
   * Restricted constructor.
   * @param builder  the builder to copy from, not null
   */
  protected CdsData(CdsData.Builder builder) {
    JodaBeanUtils.notNull(builder._interestRateNotional, "interestRateNotional");
    JodaBeanUtils.notNull(builder._coupon, "coupon");
    JodaBeanUtils.notNull(builder._buy, "buy");
    this._interestRateNotional = builder._interestRateNotional;
    this._coupon = builder._coupon;
    this._buy = builder._buy;
  }

  @Override
  public CdsData.Meta metaBean() {
    return CdsData.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the interestRateNotional.
   * @return the value of the property, not null
   */
  public InterestRateNotional getInterestRateNotional() {
    return _interestRateNotional;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the coupon.
   * @return the value of the property, not null
   */
  public double getCoupon() {
    return _coupon;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the buy.
   * @return the value of the property, not null
   */
  public boolean isBuy() {
    return _buy;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      CdsData other = (CdsData) obj;
      return JodaBeanUtils.equal(getInterestRateNotional(), other.getInterestRateNotional()) &&
          JodaBeanUtils.equal(getCoupon(), other.getCoupon()) &&
          (isBuy() == other.isBuy());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getInterestRateNotional());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCoupon());
    hash += hash * 31 + JodaBeanUtils.hashCode(isBuy());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("CdsData{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("interestRateNotional").append('=').append(JodaBeanUtils.toString(getInterestRateNotional())).append(',').append(' ');
    buf.append("coupon").append('=').append(JodaBeanUtils.toString(getCoupon())).append(',').append(' ');
    buf.append("buy").append('=').append(JodaBeanUtils.toString(isBuy())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code CdsData}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code interestRateNotional} property.
     */
    private final MetaProperty<InterestRateNotional> _interestRateNotional = DirectMetaProperty.ofImmutable(
        this, "interestRateNotional", CdsData.class, InterestRateNotional.class);
    /**
     * The meta-property for the {@code coupon} property.
     */
    private final MetaProperty<Double> _coupon = DirectMetaProperty.ofImmutable(
        this, "coupon", CdsData.class, Double.TYPE);
    /**
     * The meta-property for the {@code buy} property.
     */
    private final MetaProperty<Boolean> _buy = DirectMetaProperty.ofImmutable(
        this, "buy", CdsData.class, Boolean.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "interestRateNotional",
        "coupon",
        "buy");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 711934090:  // interestRateNotional
          return _interestRateNotional;
        case -1354573786:  // coupon
          return _coupon;
        case 97926:  // buy
          return _buy;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public CdsData.Builder builder() {
      return new CdsData.Builder();
    }

    @Override
    public Class<? extends CdsData> beanType() {
      return CdsData.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code interestRateNotional} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<InterestRateNotional> interestRateNotional() {
      return _interestRateNotional;
    }

    /**
     * The meta-property for the {@code coupon} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> coupon() {
      return _coupon;
    }

    /**
     * The meta-property for the {@code buy} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> buy() {
      return _buy;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 711934090:  // interestRateNotional
          return ((CdsData) bean).getInterestRateNotional();
        case -1354573786:  // coupon
          return ((CdsData) bean).getCoupon();
        case 97926:  // buy
          return ((CdsData) bean).isBuy();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code CdsData}.
   */
  public static class Builder extends DirectFieldsBeanBuilder<CdsData> {

    private InterestRateNotional _interestRateNotional;
    private double _coupon;
    private boolean _buy;

    /**
     * Restricted constructor.
     */
    protected Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    protected Builder(CdsData beanToCopy) {
      this._interestRateNotional = beanToCopy.getInterestRateNotional();
      this._coupon = beanToCopy.getCoupon();
      this._buy = beanToCopy.isBuy();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 711934090:  // interestRateNotional
          return _interestRateNotional;
        case -1354573786:  // coupon
          return _coupon;
        case 97926:  // buy
          return _buy;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 711934090:  // interestRateNotional
          this._interestRateNotional = (InterestRateNotional) newValue;
          break;
        case -1354573786:  // coupon
          this._coupon = (Double) newValue;
          break;
        case 97926:  // buy
          this._buy = (Boolean) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public CdsData build() {
      return new CdsData(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code interestRateNotional} property in the builder.
     * @param interestRateNotional  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder interestRateNotional(InterestRateNotional interestRateNotional) {
      JodaBeanUtils.notNull(interestRateNotional, "interestRateNotional");
      this._interestRateNotional = interestRateNotional;
      return this;
    }

    /**
     * Sets the {@code coupon} property in the builder.
     * @param coupon  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder coupon(double coupon) {
      JodaBeanUtils.notNull(coupon, "coupon");
      this._coupon = coupon;
      return this;
    }

    /**
     * Sets the {@code buy} property in the builder.
     * @param buy  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder buy(boolean buy) {
      JodaBeanUtils.notNull(buy, "buy");
      this._buy = buy;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("CdsData.Builder{");
      int len = buf.length();
      toString(buf);
      if (buf.length() > len) {
        buf.setLength(buf.length() - 2);
      }
      buf.append('}');
      return buf.toString();
    }

    protected void toString(StringBuilder buf) {
      buf.append("interestRateNotional").append('=').append(JodaBeanUtils.toString(_interestRateNotional)).append(',').append(' ');
      buf.append("coupon").append('=').append(JodaBeanUtils.toString(_coupon)).append(',').append(' ');
      buf.append("buy").append('=').append(JodaBeanUtils.toString(_buy)).append(',').append(' ');
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
