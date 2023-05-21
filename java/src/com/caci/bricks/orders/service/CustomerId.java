package com.caci.bricks.orders.service;

public interface CustomerId {
  String getIdentifier();

  static CustomerId of(String identifier) {
    return new CustomerIdImpl(identifier);
  }

  class CustomerIdImpl implements CustomerId {
    private final String identifier;

    public CustomerIdImpl(String identifier) {
      this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
      return identifier;
    }
  }
}
