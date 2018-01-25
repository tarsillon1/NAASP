package com.teamrocket.naasp.service.commons.mongo;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Condition for if mongo repositories are configured.
 */
public class MongoCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
    String property = context.getEnvironment().getProperty("env");
    return property == null || !property.equals("dev");
  }

}