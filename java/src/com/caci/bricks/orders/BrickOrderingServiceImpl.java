package com.caci.bricks.orders;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
class BrickOrderingServiceImpl implements BrickOrderingService {

  @Override
  public OrderReference placeOrder(CustomerId customerId, int quantity) {
    return null;
  }

  @Override
  public OrderDetails findOrderDetails(OrderReference orderReference) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public List<OrderDetails> findAllOrderDetails() {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public OrderReference updateOrder(OrderReference orderReference, int quantity) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  @Override
  public void fulfilOrder(OrderReference orderReference) {
    throw new UnsupportedOperationException("Method not implemented");
  }
}
