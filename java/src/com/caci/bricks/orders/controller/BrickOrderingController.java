package com.caci.bricks.orders.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.caci.bricks.orders.service.BrickOrderingService;
import com.caci.bricks.orders.service.CustomerId;
import com.caci.bricks.orders.service.CustomerOrder;
import com.caci.bricks.orders.service.CustomerOrderBuilder;
import com.caci.bricks.orders.service.CustomerOrderDetail;
import com.caci.bricks.orders.service.OrderQuantity;
import com.caci.bricks.orders.service.OrderReference;
import com.caci.bricks.orders.service.SubmissionId;

@RestController
class BrickOrderingController {
  private final BrickOrderingService brickOrderingService;

  @Autowired
  public BrickOrderingController(BrickOrderingService brickOrderingService) {
    this.brickOrderingService = brickOrderingService;
  }

  @PostMapping("/bricks/start-order")
  ResponseEntity<OrderReferenceResponse> startOrder(
    @RequestBody @Valid InitialCustomerOrderRequest initialCustomerOrderRequest
  ) {
    CustomerOrder customerOrder = CustomerOrderBuilder.newBuilder()
      .withOrderQuantity(OrderQuantity.of(initialCustomerOrderRequest.getQuantity()))
      .withCustomerId(CustomerId.of(initialCustomerOrderRequest.getCustomerId()))
      .build();

    OrderReference orderReference = brickOrderingService.startCustomerOrder(customerOrder);

    return ResponseEntity.status(HttpStatus.CREATED)
      .body(OrderReferenceResponse.Builder.newBuilder()
        .withSubmissionId(orderReference.getSubmissionId().getIdentifier())
        .build());
  }

  @GetMapping("/bricks/find-order/{id}")
  public @ResponseBody ResponseEntity<OrderDetailResponse> findOrderDetails(@PathVariable("id") String orderReference) {

    Optional<CustomerOrderDetail> customerOrderDetail = brickOrderingService.findOrder(OrderReference.of(SubmissionId.of(orderReference)));

    return customerOrderDetail.map(orderDetail -> ResponseEntity.status(HttpStatus.OK)
      .body(
        OrderDetailResponse.newBuilder()
          .withOrderReference(orderDetail.getOrderReference().getSubmissionId().getIdentifier())
          .withQuantity(orderDetail.getOrderQuantity().getQuantity())
          .build()
      )).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
