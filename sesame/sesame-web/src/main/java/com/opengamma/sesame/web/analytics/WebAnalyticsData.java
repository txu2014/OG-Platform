/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.web.analytics;

import java.util.Map;

import javax.ws.rs.core.UriInfo;

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

import com.opengamma.id.ObjectId;
import com.opengamma.sesame.server.FunctionServer;
import com.opengamma.sesame.server.FunctionServerRequest;
import com.opengamma.sesame.server.IndividualCycleOptions;
import com.opengamma.web.WebPerRequestData;

/**
 * Data class for web-based analytics.
 */
@BeanDefinition
public class WebAnalyticsData extends WebPerRequestData {

  /**
   * The function server.
   */
  @PropertyDefinition
  private FunctionServer _functionServer;
  /**
   * The view id from the input URI.
   */
  @PropertyDefinition
  private String _uriViewId;
  /**
   * The calculation request.
   */
  @PropertyDefinition
  private FunctionServerRequest<IndividualCycleOptions> _calculationRequest;

  /**
   * Creates an instance.
   */
  public WebAnalyticsData() {
  }

  /**
   * Creates an instance.
   * @param uriInfo  the URI information
   */
  public WebAnalyticsData(UriInfo uriInfo) {
    setUriInfo(uriInfo);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the best available view id.
   * <p>
   * This returns the specified object identifier, or the identifier from the
   * URI if the parameter is null.
   * 
   * @param overrideId  the override id, null derives the result from the data
   * @return the id, may be null
   */
  public String getBestViewUriId(ObjectId overrideId) {
    if (overrideId != null) {
      return overrideId.toString();
    }
    return getUriViewId();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code WebAnalyticsData}.
   * @return the meta-bean, not null
   */
  public static WebAnalyticsData.Meta meta() {
    return WebAnalyticsData.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(WebAnalyticsData.Meta.INSTANCE);
  }

  @Override
  public WebAnalyticsData.Meta metaBean() {
    return WebAnalyticsData.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the function server.
   * @return the value of the property
   */
  public FunctionServer getFunctionServer() {
    return _functionServer;
  }

  /**
   * Sets the function server.
   * @param functionServer  the new value of the property
   */
  public void setFunctionServer(FunctionServer functionServer) {
    this._functionServer = functionServer;
  }

  /**
   * Gets the the {@code functionServer} property.
   * @return the property, not null
   */
  public final Property<FunctionServer> functionServer() {
    return metaBean().functionServer().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the view id from the input URI.
   * @return the value of the property
   */
  public String getUriViewId() {
    return _uriViewId;
  }

  /**
   * Sets the view id from the input URI.
   * @param uriViewId  the new value of the property
   */
  public void setUriViewId(String uriViewId) {
    this._uriViewId = uriViewId;
  }

  /**
   * Gets the the {@code uriViewId} property.
   * @return the property, not null
   */
  public final Property<String> uriViewId() {
    return metaBean().uriViewId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the calculation request.
   * @return the value of the property
   */
  public FunctionServerRequest<IndividualCycleOptions> getCalculationRequest() {
    return _calculationRequest;
  }

  /**
   * Sets the calculation request.
   * @param calculationRequest  the new value of the property
   */
  public void setCalculationRequest(FunctionServerRequest<IndividualCycleOptions> calculationRequest) {
    this._calculationRequest = calculationRequest;
  }

  /**
   * Gets the the {@code calculationRequest} property.
   * @return the property, not null
   */
  public final Property<FunctionServerRequest<IndividualCycleOptions>> calculationRequest() {
    return metaBean().calculationRequest().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public WebAnalyticsData clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      WebAnalyticsData other = (WebAnalyticsData) obj;
      return JodaBeanUtils.equal(getFunctionServer(), other.getFunctionServer()) &&
          JodaBeanUtils.equal(getUriViewId(), other.getUriViewId()) &&
          JodaBeanUtils.equal(getCalculationRequest(), other.getCalculationRequest()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = hash * 31 + JodaBeanUtils.hashCode(getFunctionServer());
    hash = hash * 31 + JodaBeanUtils.hashCode(getUriViewId());
    hash = hash * 31 + JodaBeanUtils.hashCode(getCalculationRequest());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("WebAnalyticsData{");
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
    buf.append("functionServer").append('=').append(JodaBeanUtils.toString(getFunctionServer())).append(',').append(' ');
    buf.append("uriViewId").append('=').append(JodaBeanUtils.toString(getUriViewId())).append(',').append(' ');
    buf.append("calculationRequest").append('=').append(JodaBeanUtils.toString(getCalculationRequest())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code WebAnalyticsData}.
   */
  public static class Meta extends WebPerRequestData.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code functionServer} property.
     */
    private final MetaProperty<FunctionServer> _functionServer = DirectMetaProperty.ofReadWrite(
        this, "functionServer", WebAnalyticsData.class, FunctionServer.class);
    /**
     * The meta-property for the {@code uriViewId} property.
     */
    private final MetaProperty<String> _uriViewId = DirectMetaProperty.ofReadWrite(
        this, "uriViewId", WebAnalyticsData.class, String.class);
    /**
     * The meta-property for the {@code calculationRequest} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<FunctionServerRequest<IndividualCycleOptions>> _calculationRequest = DirectMetaProperty.ofReadWrite(
        this, "calculationRequest", WebAnalyticsData.class, (Class) FunctionServerRequest.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "functionServer",
        "uriViewId",
        "calculationRequest");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1118417605:  // functionServer
          return _functionServer;
        case 1353916204:  // uriViewId
          return _uriViewId;
        case 2094278534:  // calculationRequest
          return _calculationRequest;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends WebAnalyticsData> builder() {
      return new DirectBeanBuilder<WebAnalyticsData>(new WebAnalyticsData());
    }

    @Override
    public Class<? extends WebAnalyticsData> beanType() {
      return WebAnalyticsData.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code functionServer} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<FunctionServer> functionServer() {
      return _functionServer;
    }

    /**
     * The meta-property for the {@code uriViewId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> uriViewId() {
      return _uriViewId;
    }

    /**
     * The meta-property for the {@code calculationRequest} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<FunctionServerRequest<IndividualCycleOptions>> calculationRequest() {
      return _calculationRequest;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1118417605:  // functionServer
          return ((WebAnalyticsData) bean).getFunctionServer();
        case 1353916204:  // uriViewId
          return ((WebAnalyticsData) bean).getUriViewId();
        case 2094278534:  // calculationRequest
          return ((WebAnalyticsData) bean).getCalculationRequest();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1118417605:  // functionServer
          ((WebAnalyticsData) bean).setFunctionServer((FunctionServer) newValue);
          return;
        case 1353916204:  // uriViewId
          ((WebAnalyticsData) bean).setUriViewId((String) newValue);
          return;
        case 2094278534:  // calculationRequest
          ((WebAnalyticsData) bean).setCalculationRequest((FunctionServerRequest<IndividualCycleOptions>) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
