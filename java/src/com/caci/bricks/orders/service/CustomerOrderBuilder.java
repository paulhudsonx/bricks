package com.caci.bricks.orders.service;

public class CustomerOrderBuilder {
  private CustomerId customerId;
  private OrderQuantity orderQuantity;

  public static CustomerOrderBuilder newBuilder() {
    return new CustomerOrderBuilder();
  }

  public CustomerOrderBuilder withCustomerId(CustomerId customerId) {
    this.customerId = customerId;
    return this;
  }

  public CustomerOrderBuilder withOrderQuantity(OrderQuantity orderQuantity) {
    this.orderQuantity = orderQuantity;
    return this;
  }

  public CustomerOrder build() {
    return new CustomerOrderImpl(this);
  }

  private static class CustomerOrderImpl implements CustomerOrder {
    private final CustomerId customerId;
    private final OrderQuantity orderQuantity;

    public CustomerOrderImpl(CustomerOrderBuilder builder) {
      this.customerId = builder.customerId;
      this.orderQuantity = builder.orderQuantity;
    }

    @Override
    public CustomerId getCustomerId() {
      return customerId;
    }

    @Override
    public OrderQuantity getOrderQuantity() {
      return orderQuantity;
    }
  }
}
