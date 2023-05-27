package com.caci.bricks.orders.controller;

import com.caci.bricks.orders.service.OrderQuantity;
import com.caci.bricks.orders.service.SubmissionId;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class OrderDetailResponse {
  private final String orderReference;
  private final int quantity;

  static OrderDetailResponse of(SubmissionId submissionId, OrderQuantity orderQuantity) {
    return new OrderDetailResponse(submissionId.getIdentifier(), orderQuantity.getQuantity());
  }
}
