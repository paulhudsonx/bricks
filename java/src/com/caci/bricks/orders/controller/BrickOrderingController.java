package com.caci.bricks.orders.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.caci.bricks.orders.service.Builders;
import com.caci.bricks.orders.service.CustomerOrder;
import com.caci.bricks.orders.service.CustomerOrderDetail;
import com.caci.bricks.orders.service.OrderReference;

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
    CustomerOrder customerOrder = Builders.newCustomerOrder(
        Builders.newOrderQuantity(initialCustomerOrderRequest.getQuantity())
      );

    OrderReference orderReference = brickOrderingService.startCustomerOrder(customerOrder);

    return ResponseEntity.status(HttpStatus.CREATED)
      .body(OrderReferenceResponse.of(orderReference.getSubmissionId()));
  }

  @GetMapping("/bricks/find-order/{id}")
  public @ResponseBody ResponseEntity<OrderDetailResponse> findOrderDetails(@PathVariable("id") String orderReference) {

    Optional<CustomerOrderDetail> customerOrderDetail = brickOrderingService.findOrder(
      Builders.newOrderReference(Builders.newSubmissionId(orderReference))
    );

    return customerOrderDetail.map(orderDetail -> ResponseEntity.status(HttpStatus.OK)
      .body(
        OrderDetailResponse.of(orderDetail.getOrderReference().getSubmissionId(), orderDetail.getOrderQuantity())
      )).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @GetMapping("/bricks/find-orders")
  public @ResponseBody ResponseEntity<List<OrderDetailResponse>> findOrdersDetails() {

    List<CustomerOrderDetail> customerOrderDetail = brickOrderingService.findOrders();

    return ResponseEntity.status(HttpStatus.OK)
      .body(
        customerOrderDetail.stream()
            .map(orderDetail -> OrderDetailResponse.of(orderDetail.getOrderReference().getSubmissionId(), orderDetail.getOrderQuantity()))
          .collect(Collectors.toUnmodifiableList())
      );
  }
}
