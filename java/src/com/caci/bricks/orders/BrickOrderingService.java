package com.caci.bricks.orders;

import java.util.List;

public interface BrickOrderingService {
  OrderReference placeOrder(CustomerId customerId, int quantity);
  OrderDetails findOrderDetails(OrderReference orderReference);
  List<OrderDetails> findAllOrderDetails();
  OrderReference updateOrder(OrderReference orderReference, int quantity);
  void fulfilOrder(OrderReference orderReference);
}
