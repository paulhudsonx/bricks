package com.caci.bricks.orders.service;

public interface SubmissionId {
  String getIdentifier();

  static SubmissionId of(String identifier) {
    return new SubmissionIdImpl(identifier);
  }

  class SubmissionIdImpl implements SubmissionId {
    private final String identifier;

    public SubmissionIdImpl(String identifier) {
      this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
      return identifier;
    }
  }
}
