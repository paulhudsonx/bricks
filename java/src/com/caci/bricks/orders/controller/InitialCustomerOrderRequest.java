package com.caci.bricks.orders.controller;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
class InitialCustomerOrderRequest {
  @NotNull
  private final String customerId;
  @Min(0)
  private final Integer quantity;

  static InitialCustomerOrderRequest of(String customerId, int quantity) {
    return new InitialCustomerOrderRequest(customerId, quantity);
  }
}
