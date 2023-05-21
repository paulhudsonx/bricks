package com.caci.bricks.orders.service;

import java.util.Optional;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caci.bricks.orders.repository.BrickOrder;
import com.caci.bricks.orders.repository.BrickOrderRepository;

@Service
public class BrickOrderingService {

  private final ObjectFactory<SubmissionId> submissionIdObjectFactory;
  private final BrickOrderRepository brickOrderRepository;

  @Autowired
  public BrickOrderingService(ObjectFactory<SubmissionId> submissionIdObjectFactory,
    BrickOrderRepository brickOrderRepository) {
    this.submissionIdObjectFactory = submissionIdObjectFactory;
    this.brickOrderRepository = brickOrderRepository;
  }

  public OrderReference startCustomerOrder(CustomerOrder customerOrder) {
    SubmissionId submissionId = submissionIdObjectFactory.getObject();

    BrickOrder brickOrder = new BrickOrder();
    brickOrder.setReference(submissionId.getIdentifier());
    brickOrder.setQuantity(customerOrder.getOrderQuantity().getQuantity());
    brickOrderRepository.save(brickOrder);

    return OrderReference.of(submissionId);
  }

  public Optional<CustomerOrderDetail> findOrder(OrderReference orderReference) {
    Optional<BrickOrder> brickOrder = brickOrderRepository.findBrickOrderByReference(orderReference.getSubmissionId().getIdentifier());
    return brickOrder.map(
      b -> CustomerOrderDetail.of(
        OrderReference.of(SubmissionId.of(b.getReference())),
        OrderQuantity.of(b.getQuantity()))
      ).or(Optional::empty);
  }
}
