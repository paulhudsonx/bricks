package com.caci.bricks.orders.controller;

import com.caci.bricks.orders.service.OrderQuantity;
import com.caci.bricks.orders.service.SubmissionId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class OrderDetailResponse {
  private final @Getter String orderReference;
  private final @Getter int quantity;

  static OrderDetailResponse of(SubmissionId submissionId, OrderQuantity orderQuantity) {
    return new OrderDetailResponse(submissionId.getIdentifier(), orderQuantity.getQuantity());
  }
}
