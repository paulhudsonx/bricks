package com.caci.bricks.orders.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Builders {

  public static SubmissionId newSubmissionId(String identifier) {
    return new SubmissionIdImpl(identifier);
  }

  @RequiredArgsConstructor
  @Getter
  @EqualsAndHashCode
  private static class SubmissionIdImpl implements SubmissionId {
    private final String identifier;
  }

  public static OrderReference newOrderReference(SubmissionId submissionId) {
    return new OrderReferenceImpl(submissionId);
  }

  @RequiredArgsConstructor
  @Getter
  @EqualsAndHashCode
  private static class OrderReferenceImpl implements OrderReference {
    private final SubmissionId submissionId;
  }

  public static OrderQuantity newOrderQuantity(int quantity) {
    return new OrderQuantityImpl(quantity);
  }

  @RequiredArgsConstructor
  @Getter
  @EqualsAndHashCode
  private static class OrderQuantityImpl implements OrderQuantity {
    private final int quantity;
  }

  @Builder(builderMethodName = "customerOrderDetailBuilder")
  private static CustomerOrderDetail newCustomerOrderDetails(OrderReference orderReference, CustomerOrderDetail customerOrderDetail, OrderQuantity orderQuantity) {
    return new CustomerOrderDetailImpl(orderReference, customerOrderDetail, orderQuantity);
  }

  @RequiredArgsConstructor
  @Getter
  @EqualsAndHashCode
  private static class CustomerOrderDetailImpl implements CustomerOrderDetail {
    private final OrderReference orderReference;
    private final CustomerOrderDetail customerOrderDetail;
    private final OrderQuantity orderQuantity;
  }

  public static CustomerOrder newCustomerOrder(OrderQuantity orderQuantity) {
    return new CustomerOrderImpl(orderQuantity);
  }

  @RequiredArgsConstructor
  @Getter
  @EqualsAndHashCode
  private static class CustomerOrderImpl implements CustomerOrder {
    private final OrderQuantity orderQuantity;
  }
}
