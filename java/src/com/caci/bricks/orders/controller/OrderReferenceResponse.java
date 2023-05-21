package com.caci.bricks.orders.controller;

public class OrderReferenceResponse {
  private String submissionId;

  public OrderReferenceResponse() {
  }

  private OrderReferenceResponse(Builder builder) {
    submissionId = builder.submissionId;
  }


  public static final class Builder {

    private String submissionId;

    private Builder() {
    }

    public static Builder newBuilder() {
      return new Builder();
    }

    public Builder withSubmissionId(String val) {
      submissionId = val;
      return this;
    }

    public OrderReferenceResponse build() {
      return new OrderReferenceResponse(this);
    }
  }

  public void setSubmissionId(String submissionId) {
    this.submissionId = submissionId;
  }

  public String getSubmissionId() {
    return submissionId;
  }
}
