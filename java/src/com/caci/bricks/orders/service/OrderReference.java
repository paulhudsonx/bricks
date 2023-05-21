package com.caci.bricks.orders.service;

public interface OrderReference {
  SubmissionId getSubmissionId();

  static OrderReference of(SubmissionId submissionId) {
    return new OrderReferenceImpl(submissionId);
  }

  class OrderReferenceImpl implements OrderReference {
    private final SubmissionId submissionId;

    public OrderReferenceImpl(SubmissionId submissionId) {
      this.submissionId = submissionId;
    }

    @Override
    public SubmissionId getSubmissionId() {
      return submissionId;
    }
  }
}
