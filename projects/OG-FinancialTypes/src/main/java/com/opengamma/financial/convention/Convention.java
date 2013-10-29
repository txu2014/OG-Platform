/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.convention;

import java.io.Serializable;
import java.util.Map;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.id.ExternalIdBundle;
import com.opengamma.id.MutableUniqueIdentifiable;
import com.opengamma.id.UniqueId;

/**
 * Base class for market conventions that are used in curve and security construction.
 */
@BeanDefinition
public class Convention extends DirectBean implements Serializable, MutableUniqueIdentifiable {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The convention name.
   */
  @PropertyDefinition(validate = "notNull")
  private String _name = "";
  /**
   * The unique identifier of the convention.
   */
  @PropertyDefinition
  private UniqueId _uniqueId;
  /**
   * The bundle of external identifiers for this convention.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalIdBundle _externalIdBundle = ExternalIdBundle.EMPTY;

  /**
   * For the builder.
   */
  protected Convention() {
  }

  /**
   * @param name The name of the convention, not null
   * @param externalIdBundle The external identifiers for this convention, not null
   */
  public Convention(final String name, final ExternalIdBundle externalIdBundle) {
    setName(name);
    setExternalIdBundle(externalIdBundle);
  }

  //-------------------------------------------------------------------------
  /**
   * Accepts a visitor to manage traversal of the hierarchy.
   *
   * @param <T>  the result type of the visitor
   * @param visitor  the visitor, not null
   * @return the result, this implementation throws IllegalStateException
   * @throws IllegalStateException This method must be overridden in subclasses to be used.
   */
  public <T> T accept(final ConventionVisitor<T> visitor) {
    throw new IllegalStateException("Unknown Convention type " + getClass());
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code Convention}.
   * @return the meta-bean, not null
   */
  public static Convention.Meta meta() {
    return Convention.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(Convention.Meta.INSTANCE);
  }

  @Override
  public Convention.Meta metaBean() {
    return Convention.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the convention name.
   * @return the value of the property, not null
   */
  public String getName() {
    return _name;
  }

  /**
   * Sets the convention name.
   * @param name  the new value of the property, not null
   */
  public void setName(String name) {
    JodaBeanUtils.notNull(name, "name");
    this._name = name;
  }

  /**
   * Gets the the {@code name} property.
   * @return the property, not null
   */
  public final Property<String> name() {
    return metaBean().name().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the unique identifier of the convention.
   * @return the value of the property
   */
  public UniqueId getUniqueId() {
    return _uniqueId;
  }

  /**
   * Sets the unique identifier of the convention.
   * @param uniqueId  the new value of the property
   */
  public void setUniqueId(UniqueId uniqueId) {
    this._uniqueId = uniqueId;
  }

  /**
   * Gets the the {@code uniqueId} property.
   * @return the property, not null
   */
  public final Property<UniqueId> uniqueId() {
    return metaBean().uniqueId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the bundle of external identifiers for this convention.
   * @return the value of the property, not null
   */
  public ExternalIdBundle getExternalIdBundle() {
    return _externalIdBundle;
  }

  /**
   * Sets the bundle of external identifiers for this convention.
   * @param externalIdBundle  the new value of the property, not null
   */
  public void setExternalIdBundle(ExternalIdBundle externalIdBundle) {
    JodaBeanUtils.notNull(externalIdBundle, "externalIdBundle");
    this._externalIdBundle = externalIdBundle;
  }

  /**
   * Gets the the {@code externalIdBundle} property.
   * @return the property, not null
   */
  public final Property<ExternalIdBundle> externalIdBundle() {
    return metaBean().externalIdBundle().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public Convention clone() {
    BeanBuilder<? extends Convention> builder = metaBean().builder();
    for (MetaProperty<?> mp : metaBean().metaPropertyIterable()) {
      if (mp.style().isBuildable()) {
        Object value = mp.get(this);
        if (value instanceof Bean) {
          value = ((Bean) value).clone();
        }
        builder.set(mp.name(), value);
      }
    }
    return builder.build();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      Convention other = (Convention) obj;
      return JodaBeanUtils.equal(getName(), other.getName()) &&
          JodaBeanUtils.equal(getUniqueId(), other.getUniqueId()) &&
          JodaBeanUtils.equal(getExternalIdBundle(), other.getExternalIdBundle());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUniqueId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getExternalIdBundle());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("Convention{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("name").append('=').append(JodaBeanUtils.toString(getName())).append(',').append(' ');
    buf.append("uniqueId").append('=').append(JodaBeanUtils.toString(getUniqueId())).append(',').append(' ');
    buf.append("externalIdBundle").append('=').append(JodaBeanUtils.toString(getExternalIdBundle())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code Convention}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> _name = DirectMetaProperty.ofReadWrite(
        this, "name", Convention.class, String.class);
    /**
     * The meta-property for the {@code uniqueId} property.
     */
    private final MetaProperty<UniqueId> _uniqueId = DirectMetaProperty.ofReadWrite(
        this, "uniqueId", Convention.class, UniqueId.class);
    /**
     * The meta-property for the {@code externalIdBundle} property.
     */
    private final MetaProperty<ExternalIdBundle> _externalIdBundle = DirectMetaProperty.ofReadWrite(
        this, "externalIdBundle", Convention.class, ExternalIdBundle.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "name",
        "uniqueId",
        "externalIdBundle");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          return _name;
        case -294460212:  // uniqueId
          return _uniqueId;
        case -736922008:  // externalIdBundle
          return _externalIdBundle;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends Convention> builder() {
      return new DirectBeanBuilder<Convention>(new Convention());
    }

    @Override
    public Class<? extends Convention> beanType() {
      return Convention.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> name() {
      return _name;
    }

    /**
     * The meta-property for the {@code uniqueId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<UniqueId> uniqueId() {
      return _uniqueId;
    }

    /**
     * The meta-property for the {@code externalIdBundle} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalIdBundle> externalIdBundle() {
      return _externalIdBundle;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          return ((Convention) bean).getName();
        case -294460212:  // uniqueId
          return ((Convention) bean).getUniqueId();
        case -736922008:  // externalIdBundle
          return ((Convention) bean).getExternalIdBundle();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3373707:  // name
          ((Convention) bean).setName((String) newValue);
          return;
        case -294460212:  // uniqueId
          ((Convention) bean).setUniqueId((UniqueId) newValue);
          return;
        case -736922008:  // externalIdBundle
          ((Convention) bean).setExternalIdBundle((ExternalIdBundle) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((Convention) bean)._name, "name");
      JodaBeanUtils.notNull(((Convention) bean)._externalIdBundle, "externalIdBundle");
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
