package com.caci.bricks.orders.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface BrickOrderRepository extends CrudRepository<BrickOrder, Long> {

  Optional<BrickOrder> findBrickOrderByReference(String reference);
}
