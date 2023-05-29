package com.caci.bricks.orders.service;

import static com.caci.bricks.orders.service.Builders.newOrderQuantity;
import static com.caci.bricks.orders.service.Builders.newOrderReference;
import static com.caci.bricks.orders.service.Builders.newSubmissionId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    brickOrderRepository.save(
      BrickOrder.builder()
        .reference(submissionId.getIdentifier())
        .quantity(customerOrder.getOrderQuantity().getQuantity())
        .build()
    );

    return newOrderReference(submissionId);
  }

  public Optional<CustomerOrderDetail> findOrder(OrderReference orderReference) {
    return brickOrderRepository.findBrickOrderByReference(
        orderReference.getSubmissionId().getIdentifier())
      .map(
        brickOrder -> Builders.customerOrderDetailBuilder()
          .orderReference(newOrderReference(newSubmissionId(brickOrder.getReference())))
          .orderQuantity(newOrderQuantity(brickOrder.getQuantity()))
          .build()
      ).or(Optional::empty);
  }

  public List<CustomerOrderDetail> findOrders() {
    return StreamSupport.stream(brickOrderRepository.findAll()
        .spliterator(), false)
      .map(
        brickOrder -> Builders.customerOrderDetailBuilder()
          .orderReference(newOrderReference(newSubmissionId(brickOrder.getReference())))
          .orderQuantity(newOrderQuantity(brickOrder.getQuantity()))
          .build()
      )
      .collect(Collectors.toUnmodifiableList());
  }
}
