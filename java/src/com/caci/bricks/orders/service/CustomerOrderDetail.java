package com.caci.bricks.orders.service;

public interface CustomerOrderDetail {
  OrderReference getOrderReference();
  OrderQuantity getOrderQuantity();

  static CustomerOrderDetail of(OrderReference orderReference, OrderQuantity orderQuantity) {
    return new CustomerOrderDetailImpl(orderReference, orderQuantity);
  }

  class CustomerOrderDetailImpl implements CustomerOrderDetail {
    private final OrderReference orderReference;
    private final OrderQuantity orderQuantity;

    public CustomerOrderDetailImpl(OrderReference orderReference, OrderQuantity orderQuantity) {
      this.orderReference = orderReference;
      this.orderQuantity = orderQuantity;
    }

    @Override
    public OrderReference getOrderReference() {
      return orderReference;
    }

    @Override
    public OrderQuantity getOrderQuantity() {
      return orderQuantity;
    }
  }
}
