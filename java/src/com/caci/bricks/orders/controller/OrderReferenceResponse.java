package com.caci.bricks.orders.controller;

import com.caci.bricks.orders.service.SubmissionId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
class OrderReferenceResponse {
  private final String submissionId;

  static OrderReferenceResponse of(SubmissionId submissionId) {
    return new OrderReferenceResponse(submissionId.getIdentifier());
  }
}
