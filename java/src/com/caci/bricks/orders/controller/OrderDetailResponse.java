package com.caci.bricks.orders.controller;

import com.caci.bricks.orders.service.OrderQuantity;
import com.caci.bricks.orders.service.SubmissionId;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
class OrderDetailResponse {
  private final String orderReference;
  private final int quantity;

  static OrderDetailResponse of(SubmissionId submissionId, OrderQuantity orderQuantity) {
    return of(submissionId.getIdentifier(), orderQuantity.getQuantity());
  }

  static OrderDetailResponse of(String identifier, int quantity) {
    return new OrderDetailResponse(identifier, quantity);
  }
}
