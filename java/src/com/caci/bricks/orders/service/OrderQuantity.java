package com.caci.bricks.orders.service;

public interface OrderQuantity {
  int getQuantity();

  static OrderQuantity of(int quantity) {
    return new OrderQuantityImpl(quantity);
  }

  class OrderQuantityImpl implements OrderQuantity {
    private final int quantity;

    public OrderQuantityImpl(int quantity) {
      this.quantity = quantity;
    }

    @Override
    public int getQuantity() {
      return quantity;
    }
  }
}
