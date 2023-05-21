package com.caci.bricks.orders.controller;

class OrderDetailResponse {
  private String orderReference;
  private int quantity;

  public OrderDetailResponse() {
  }

  private OrderDetailResponse(Builder builder) {
    orderReference = builder.orderReference;
    quantity = builder.quantity;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder {

    private String orderReference;
    private int quantity;

    private Builder() {
    }

    public Builder withOrderReference(String val) {
      orderReference = val;
      return this;
    }

    public Builder withQuantity(int val) {
      quantity = val;
      return this;
    }

    public OrderDetailResponse build() {
      return new OrderDetailResponse(this);
    }
  }

  public void setOrderReference(String orderReference) {
    this.orderReference = orderReference;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getOrderReference() {
    return orderReference;
  }

  public int getQuantity() {
    return quantity;
  }
}
