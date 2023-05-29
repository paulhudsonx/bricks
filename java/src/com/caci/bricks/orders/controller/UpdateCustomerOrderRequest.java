package com.caci.bricks.orders.controller;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UpdateCustomerOrderRequest {
  @NotEmpty
  private final String orderReference;
  @Min(0)
  private final int quantity;

  public static UpdateCustomerOrderRequest of(String orderReference, int quantity) {
    return new UpdateCustomerOrderRequest(orderReference, quantity);
  }
}
