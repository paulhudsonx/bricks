package com.caci.bricks.orders.controller;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class InitialCustomerOrderRequest {
  @NotNull
  private final @Getter String customerId;
  @Min(0)
  private final @Getter Integer quantity;

  static InitialCustomerOrderRequest of(String customerId, int quantity) {
    return new InitialCustomerOrderRequest(customerId, quantity);
  }
}
