package com.caci.bricks.orders.controller;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

class InitialCustomerOrderRequest {
  @NotNull
  private String customerId;
  @Min(0)
  private Integer quantity;

  static InitialCustomerOrderRequest of(String customerId, int quantity) {
    InitialCustomerOrderRequest that = new InitialCustomerOrderRequest();
    that.customerId = customerId;
    that.quantity = quantity;
    return that;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
