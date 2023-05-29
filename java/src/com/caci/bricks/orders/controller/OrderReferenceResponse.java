package com.caci.bricks.orders.controller;

import com.caci.bricks.orders.service.SubmissionId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode
class OrderReferenceResponse {
  private String submissionId;

  static OrderReferenceResponse of(SubmissionId submissionId) {
    return new OrderReferenceResponse(submissionId.getIdentifier());
  }
}
