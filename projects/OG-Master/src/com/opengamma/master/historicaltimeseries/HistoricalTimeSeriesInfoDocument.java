/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.historicaltimeseries;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.DerivedProperty;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.id.UniqueIdentifier;
import com.opengamma.master.AbstractDocument;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.PublicSPI;

/**
 * A document used to pass into and out of the historical time-series master.
 * <p>
 * This document stores information about the time-series, not the time-series itself.
 * <p>
 * This class is mutable and not thread-safe.
 */
@PublicSPI
@BeanDefinition
public class HistoricalTimeSeriesInfoDocument extends AbstractDocument {

  /**
   * The information about the time-series.
   */
  @PropertyDefinition(validate = "notNull")
  private ManageableHistoricalTimeSeriesInfo _info;

  /**
   * Creates an instance.
   */
  public HistoricalTimeSeriesInfoDocument() {
    _info = new ManageableHistoricalTimeSeriesInfo();
  }

  /**
   * Creates an instance.
   * 
   * @param info  the information about the time-series, not null
   */
  public HistoricalTimeSeriesInfoDocument(ManageableHistoricalTimeSeriesInfo info) {
    ArgumentChecker.notNull(info, "info");
    setInfo(info);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the historical time-series unique identifier.
   * This gets the unique identifier from the {@code ManageableHistoricalTimeSeries}.
   * 
   * @return the unique identifier
   */
  @DerivedProperty
  public UniqueIdentifier getUniqueId() {
    return getInfo().getUniqueId();
  }

  /**
   * Sets the historical time-series unique identifier.
   * This sets the unique identifier in the {@code ManageableHistoricalTimeSeries}.
   * 
   * @param uniqueId  the new unique identifier
   */
  public void setUniqueId(UniqueIdentifier uniqueId) {
    getInfo().setUniqueId(uniqueId);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code HistoricalTimeSeriesInfoDocument}.
   * @return the meta-bean, not null
   */
  public static HistoricalTimeSeriesInfoDocument.Meta meta() {
    return HistoricalTimeSeriesInfoDocument.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(HistoricalTimeSeriesInfoDocument.Meta.INSTANCE);
  }

  @Override
  public HistoricalTimeSeriesInfoDocument.Meta metaBean() {
    return HistoricalTimeSeriesInfoDocument.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 3237038:  // info
        return getInfo();
      case -294460212:  // uniqueId
        return getUniqueId();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 3237038:  // info
        setInfo((ManageableHistoricalTimeSeriesInfo) newValue);
        return;
      case -294460212:  // uniqueId
        if (quiet) {
          return;
        }
        throw new UnsupportedOperationException("Property cannot be written: uniqueId");
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_info, "info");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      HistoricalTimeSeriesInfoDocument other = (HistoricalTimeSeriesInfoDocument) obj;
      return JodaBeanUtils.equal(getInfo(), other.getInfo()) &&
          JodaBeanUtils.equal(getUniqueId(), other.getUniqueId()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getInfo());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUniqueId());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the information about the time-series.
   * @return the value of the property, not null
   */
  public ManageableHistoricalTimeSeriesInfo getInfo() {
    return _info;
  }

  /**
   * Sets the information about the time-series.
   * @param info  the new value of the property, not null
   */
  public void setInfo(ManageableHistoricalTimeSeriesInfo info) {
    JodaBeanUtils.notNull(info, "info");
    this._info = info;
  }

  /**
   * Gets the the {@code info} property.
   * @return the property, not null
   */
  public final Property<ManageableHistoricalTimeSeriesInfo> info() {
    return metaBean().info().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the the {@code uniqueId} property.
   * This gets the unique identifier from the {@code ManageableHistoricalTimeSeries}.
   * 
   * @return the property, not null
   */
  public final Property<UniqueIdentifier> uniqueId() {
    return metaBean().uniqueId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code HistoricalTimeSeriesInfoDocument}.
   */
  public static class Meta extends AbstractDocument.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code info} property.
     */
    private final MetaProperty<ManageableHistoricalTimeSeriesInfo> _info = DirectMetaProperty.ofReadWrite(
        this, "info", HistoricalTimeSeriesInfoDocument.class, ManageableHistoricalTimeSeriesInfo.class);
    /**
     * The meta-property for the {@code uniqueId} property.
     */
    private final MetaProperty<UniqueIdentifier> _uniqueId = DirectMetaProperty.ofReadOnly(
        this, "uniqueId", HistoricalTimeSeriesInfoDocument.class, UniqueIdentifier.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "info",
        "uniqueId");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3237038:  // info
          return _info;
        case -294460212:  // uniqueId
          return _uniqueId;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends HistoricalTimeSeriesInfoDocument> builder() {
      return new DirectBeanBuilder<HistoricalTimeSeriesInfoDocument>(new HistoricalTimeSeriesInfoDocument());
    }

    @Override
    public Class<? extends HistoricalTimeSeriesInfoDocument> beanType() {
      return HistoricalTimeSeriesInfoDocument.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code info} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ManageableHistoricalTimeSeriesInfo> info() {
      return _info;
    }

    /**
     * The meta-property for the {@code uniqueId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<UniqueIdentifier> uniqueId() {
      return _uniqueId;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
