package com.caci.bricks.orders.repository;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Access(AccessType.PROPERTY)
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrickOrder {

  private Long id;
  private String reference;
  private int quantity;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long getId() {
    return id;
  }

  @Column(unique = true)
  public String getReference() {
    return reference;
  }
}
