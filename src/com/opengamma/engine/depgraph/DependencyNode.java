/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.depgraph;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;

import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.function.FunctionDefinition;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.util.ArgumentChecker;

// REVIEW kirk 2009-12-29 -- This is ultimately intended to replace DependencyNode once
// the current rewrite is done.

/**
 * 
 *
 * @author kirk
 */
public class DependencyNode {
  private final FunctionDefinition _functionDefinition;
  private final ComputationTarget _computationTarget;
  private final Set<ValueRequirement> _inputRequirements = new HashSet<ValueRequirement>();
  private final Set<ValueSpecification> _outputValues = new HashSet<ValueSpecification>();
  private final Set<DependencyNode> _inputNodes = new HashSet<DependencyNode>();
  private final Map<ValueRequirement, ValueSpecification> _requirementMapping =
    new HashMap<ValueRequirement, ValueSpecification>();
  private final Set<DependencyNode> _dependentNodes = new HashSet<DependencyNode>();
  
  public DependencyNode(
      FunctionCompilationContext context,
      FunctionDefinition functionDefinition,
      ComputationTarget target) {
    ArgumentChecker.checkNotNull(functionDefinition, "Function Definition");
    ArgumentChecker.checkNotNull(target, "Computation Target");
    if(functionDefinition.getTargetType() != target.getType()) {
      throw new IllegalArgumentException(
          "Provided function of type " + functionDefinition.getTargetType()
          + " but target of type " + target.getType());
    }
    
    _functionDefinition = functionDefinition;
    _computationTarget = target;
    _inputRequirements.addAll(_functionDefinition.getRequirements(context, _computationTarget));
    _outputValues.addAll(_functionDefinition.getResults(context, _computationTarget, _inputRequirements));
  }
  
  public void addInputNode(DependencyNode inputNode) {
    ArgumentChecker.checkNotNull(inputNode, "Input Node");
    _inputNodes.add(inputNode);
    inputNode.addDependentNode(this); // note how we rely on the yucky class-scope encapsulation of private
  }
 
  private void addDependentNode(DependencyNode dependentNode) {
    ArgumentChecker.checkNotNull(dependentNode, "Dependent Node");
    _dependentNodes.add(dependentNode);
  }
  
  public Set<DependencyNode> getDependentNodes() {
    return Collections.unmodifiableSet(_dependentNodes);
  }
  
  public Set<DependencyNode> getInputNodes() {
    return Collections.unmodifiableSet(_inputNodes);
  }
  
  public Set<ValueSpecification> getOutputValues() {
    return Collections.unmodifiableSet(_outputValues);
  }
  
  public Set<ValueRequirement> getInputRequirements() {
    return Collections.unmodifiableSet(_inputRequirements);
  }
  
  /**
   * @return the functionDefinition
   */
  public FunctionDefinition getFunctionDefinition() {
    return _functionDefinition;
  }

  /**
   * @return the computationTarget
   */
  public ComputationTarget getComputationTarget() {
    return _computationTarget;
  }

  public void addRequirementMapping(ValueRequirement inputRequirement, ValueSpecification actualValue) {
    ArgumentChecker.checkNotNull(inputRequirement, "Input Value Requirement");
    ArgumentChecker.checkNotNull(actualValue, "Actual Value Specification");
    assert inputNodeProduces(actualValue);
    _requirementMapping.put(inputRequirement, actualValue);
  }
  
  public ValueSpecification getMappedRequirement(ValueRequirement inputRequirement) {
    return _requirementMapping.get(inputRequirement);
  }
  
  protected boolean inputNodeProduces(ValueSpecification value) {
    for(DependencyNode inputNode : _inputNodes) {
      if(inputNode.getOutputValues().contains(value)) {
        return true;
      }
    }
    return false;
  }
  
  public ValueSpecification satisfiesRequirement(ValueRequirement requirement) {
    if(requirement.getTargetSpecification().getType() != getComputationTarget().getType()) {
      return null;
    }
    if(!ObjectUtils.equals(requirement.getTargetSpecification().getIdentifier(), getComputationTarget().getUniqueIdentifier())) {
      return null;
    }
    for(ValueSpecification outputSpec : _outputValues) {
      if(ObjectUtils.equals(outputSpec.getRequirementSpecification(), requirement)) {
        return outputSpec;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("DependencyNode[");
    sb.append(getFunctionDefinition().getShortName());
    sb.append(" on ");
    sb.append(getComputationTarget().getSpecification());
    sb.append("]");
    return sb.toString();
  }
  
}
