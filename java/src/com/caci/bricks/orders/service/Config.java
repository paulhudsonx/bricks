package com.caci.bricks.orders.service;

import java.util.UUID;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class Config {

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public SubmissionId submissionId() {
    return Builders.newSubmissionId(UUID.randomUUID().toString());
  }
}
