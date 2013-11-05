/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.proxy;

import static com.opengamma.sesame.config.ConfigBuilder.config;
import static com.opengamma.sesame.config.ConfigBuilder.implementations;
import static org.testng.AssertJUnit.assertEquals;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.opengamma.sesame.config.ConfigUtils;
import com.opengamma.sesame.config.FunctionConfig;
import com.opengamma.sesame.config.GraphConfig;
import com.opengamma.sesame.engine.ComponentMap;
import com.opengamma.sesame.function.FunctionMetadata;
import com.opengamma.sesame.function.Output;
import com.opengamma.sesame.graph.FunctionModel;
import com.opengamma.sesame.graph.InterfaceNode;
import com.opengamma.util.test.TestGroup;

@Test(groups = TestGroup.UNIT)
public class ProxyNodeTest {

  @Test
  public void proxy() {
    FunctionConfig config = config(implementations(TestFn.class, Impl.class));
    GraphConfig graphConfig = new GraphConfig(config, ComponentMap.EMPTY, new Decorator());
    FunctionMetadata metadata = ConfigUtils.createMetadata(TestFn.class, "foo");
    FunctionModel functionModel = FunctionModel.forFunction(metadata, graphConfig);
    TestFn fn = (TestFn) functionModel.build(ComponentMap.EMPTY).getReceiver();
    assertEquals(Lists.newArrayList("proxied", "foo"), fn.foo());
  }

  interface TestFn {

    @Output("foo")
    Object foo();
  }

  public class Impl implements TestFn {


    @Override
    public Object foo() {
      return "foo";
    }
  }

  class Decorator extends ProxyNodeDecorator {

    @Override
    protected boolean decorate(InterfaceNode node) {
      return node.getInterfaceType().equals(TestFn.class);
    }

    @Override
    protected Object invoke(Object proxy, Object delegate, Method method, Object[] args) throws Exception {
      return Lists.newArrayList("proxied", method.invoke(delegate, args));
    }
  }
}
