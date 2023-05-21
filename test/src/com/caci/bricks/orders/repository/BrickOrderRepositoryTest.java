package com.caci.bricks.orders.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BrickOrderRepositoryTest {

  @Autowired
  private BrickOrderRepository brickOrderRepository;

  @BeforeEach
  void beforeEach() {
    brickOrderRepository.deleteAll();
  }

  @Test
  @DisplayName("Should return an existing order by its current reference")
  public void shouldFindExistingOrderByReference() {
    //given
    BrickOrder brickOrder = new BrickOrder();
    brickOrder.setReference("11111111");
    brickOrder.setQuantity(1);
    brickOrderRepository.save(brickOrder);

    //when
    Optional<BrickOrder> actual = brickOrderRepository.findBrickOrderByReference("11111111");

    //then
    assertThat(actual.isPresent(), is(true));
    assertThat(actual.get().getQuantity(), is(1));
  }

  @Test
  @DisplayName("Should not return an existing order if the target reference is incorrect")
  public void shouldNotFindExistingOrderByIncorrectReference() {
    //given
    BrickOrder brickOrder = new BrickOrder();
    brickOrder.setReference("11111111");
    brickOrderRepository.save(brickOrder);

    //when
    Optional<BrickOrder> actual = brickOrderRepository.findBrickOrderByReference("22222222");

    //then
    assertThat(actual.isPresent(), is(false));
  }

  @Test
  @DisplayName("Should find all existing customer orders")
  public void shouldFindExistingCustomerOrders() {
    //given
    BrickOrder brickOrder1 = new BrickOrder();
    brickOrder1.setReference("11111111");
    brickOrderRepository.save(brickOrder1);
    BrickOrder brickOrder2 = new BrickOrder();
    brickOrder2.setReference("22222222");
    brickOrderRepository.save(brickOrder2);

    //when
    Iterable<BrickOrder> actual = brickOrderRepository.findAll();

    //then
    assertThat(actual, is(notNullValue()));
    assertThat(StreamSupport.stream(actual.spliterator(), false).count(), is(2L));
  }
}
