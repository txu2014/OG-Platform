/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.sabr;

import java.util.HashMap;
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

import com.google.common.collect.ImmutableMap;
import com.opengamma.core.config.Config;
import com.opengamma.financial.security.FinancialSecurity;
import com.opengamma.financial.security.option.SwaptionSecurity;
import com.opengamma.util.result.FailureStatus;
import com.opengamma.util.result.Result;

/**
 * Responsible for determining what SABR data is required for
 * the trade/security that is passed. Contains a map from
 * security to the SABR config to be used.
 */
@BeanDefinition
@Config(group = "SABR Params", description = "SABR Config Selector")
public class SabrConfigSelector implements ImmutableBean {

  @PropertyDefinition
  private final Map<Class<? extends FinancialSecurity>, SabrSwaptionConfig> _configurations;

  /**
   * Get the SABR config to use for a particular security.
   *
   * @param security the security to find the config for
   * @return Result containing the configuration to use
   * if available, a FailureResult otherwise
   */
  public Result<SabrParametersConfiguration> getSabrConfig(FinancialSecurity security) {

    if (_configurations.containsKey(security.getClass())) {
      SabrSwaptionConfig config = _configurations.get(security.getClass());
      return config.createSABRParametersConfig((SwaptionSecurity) security);
    } else {
      return Result.failure(
          FailureStatus.MISSING_DATA, "Unable to get SABR config for security of type: {}", security.getClass());
    }
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code SabrConfigSelector}.
   * @return the meta-bean, not null
   */
  public static SabrConfigSelector.Meta meta() {
    return SabrConfigSelector.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(SabrConfigSelector.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static SabrConfigSelector.Builder builder() {
    return new SabrConfigSelector.Builder();
  }

  /**
   * Restricted constructor.
   * @param builder  the builder to copy from, not null
   */
  protected SabrConfigSelector(SabrConfigSelector.Builder builder) {
    this._configurations = (builder._configurations != null ? ImmutableMap.copyOf(builder._configurations) : null);
  }

  @Override
  public SabrConfigSelector.Meta metaBean() {
    return SabrConfigSelector.Meta.INSTANCE;
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
   * Gets the configurations.
   * @return the value of the property
   */
  public Map<Class<? extends FinancialSecurity>, SabrSwaptionConfig> getConfigurations() {
    return _configurations;
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
      SabrConfigSelector other = (SabrConfigSelector) obj;
      return JodaBeanUtils.equal(getConfigurations(), other.getConfigurations());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getConfigurations());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("SabrConfigSelector{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("configurations").append('=').append(JodaBeanUtils.toString(getConfigurations())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code SabrConfigSelector}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code configurations} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Map<Class<? extends FinancialSecurity>, SabrSwaptionConfig>> _configurations = DirectMetaProperty.ofImmutable(
        this, "configurations", SabrConfigSelector.class, (Class) Map.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "configurations");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -214226371:  // configurations
          return _configurations;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public SabrConfigSelector.Builder builder() {
      return new SabrConfigSelector.Builder();
    }

    @Override
    public Class<? extends SabrConfigSelector> beanType() {
      return SabrConfigSelector.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code configurations} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Map<Class<? extends FinancialSecurity>, SabrSwaptionConfig>> configurations() {
      return _configurations;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -214226371:  // configurations
          return ((SabrConfigSelector) bean).getConfigurations();
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
   * The bean-builder for {@code SabrConfigSelector}.
   */
  public static class Builder extends DirectFieldsBeanBuilder<SabrConfigSelector> {

    private Map<Class<? extends FinancialSecurity>, SabrSwaptionConfig> _configurations;

    /**
     * Restricted constructor.
     */
    protected Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    protected Builder(SabrConfigSelector beanToCopy) {
      this._configurations = (beanToCopy.getConfigurations() != null ? new HashMap<Class<? extends FinancialSecurity>, SabrSwaptionConfig>(beanToCopy.getConfigurations()) : null);
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -214226371:  // configurations
          return _configurations;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -214226371:  // configurations
          this._configurations = (Map<Class<? extends FinancialSecurity>, SabrSwaptionConfig>) newValue;
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
    public SabrConfigSelector build() {
      return new SabrConfigSelector(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code configurations} property in the builder.
     * @param configurations  the new value
     * @return this, for chaining, not null
     */
    public Builder configurations(Map<Class<? extends FinancialSecurity>, SabrSwaptionConfig> configurations) {
      this._configurations = configurations;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(64);
      buf.append("SabrConfigSelector.Builder{");
      int len = buf.length();
      toString(buf);
      if (buf.length() > len) {
        buf.setLength(buf.length() - 2);
      }
      buf.append('}');
      return buf.toString();
    }

    protected void toString(StringBuilder buf) {
      buf.append("configurations").append('=').append(JodaBeanUtils.toString(_configurations)).append(',').append(' ');
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
