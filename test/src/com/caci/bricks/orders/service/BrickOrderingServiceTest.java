package com.caci.bricks.orders.service;

import static com.caci.bricks.orders.service.Builders.customerOrderDetailBuilder;
import static com.caci.bricks.orders.service.Builders.newCustomerOrder;
import static com.caci.bricks.orders.service.Builders.newOrderQuantity;
import static com.caci.bricks.orders.service.Builders.newOrderReference;
import static com.caci.bricks.orders.service.Builders.newSubmissionId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectFactory;

import com.caci.bricks.orders.repository.BrickOrder;
import com.caci.bricks.orders.repository.BrickOrderRepository;

@ExtendWith(MockitoExtension.class)
public class BrickOrderingServiceTest {

  @Mock
  private ObjectFactory<SubmissionId> submissionIdObjectFactory;

  @Mock
  private BrickOrderRepository brickOrderRepository;

  @Captor
  private ArgumentCaptor<BrickOrder> brickOrder;

  @InjectMocks
  private BrickOrderingService brickOrderingService;

  @Test
  @DisplayName("Given a valid customer order when the customer order started then a unique reference is returned")
  public void shouldStartCustomerOrderWithUniqueReference() {
    //given
    CustomerOrder customerOrder = newCustomerOrder(newOrderQuantity(1));

    when(submissionIdObjectFactory.getObject()).thenReturn(newSubmissionId("bar"));

    //when
    OrderReference actual = brickOrderingService.startCustomerOrder(customerOrder);

    //then
    assertThat(actual, samePropertyValuesAs(newOrderReference(newSubmissionId("bar"))));
    verify(brickOrderRepository).save(any());
  }

  @Test
  @DisplayName("Given a valid customer order when the customer order started then a new order is saved")
  public void shouldSaveCustomerOrder() {
    //given
    CustomerOrder customerOrder = newCustomerOrder(newOrderQuantity(1));
    when(submissionIdObjectFactory.getObject()).thenReturn(newSubmissionId("bar"));

    //when
    brickOrderingService.startCustomerOrder(customerOrder);

    //then
    verify(brickOrderRepository).save(brickOrder.capture());
    assertThat(brickOrder.getValue(),
      samePropertyValuesAs(BrickOrder.builder().quantity(1).reference("bar").build()));
  }

  @Test
  @DisplayName("Should find an existing order when order reference matches")
  public void shouldFindExistingCustomerOrder() {
    //given
    OrderReference orderReference = newOrderReference(newSubmissionId("abc"));
    BrickOrder brickOrder1 = BrickOrder.builder()
      .reference("abc")
      .quantity(10)
      .build();
    when(brickOrderRepository.findBrickOrderByReference("abc")).thenReturn(
      Optional.of(brickOrder1));

    //when
    Optional<CustomerOrderDetail> actual = brickOrderingService.findOrder(orderReference);

    //then
    assertThat(actual.isPresent(), is(true));
    assertThat(actual.get().getOrderQuantity(), samePropertyValuesAs(newOrderQuantity(10)));
    assertThat(actual.get().getOrderReference().getSubmissionId(),
      samePropertyValuesAs(newSubmissionId("abc")));
  }

  @Test
  @DisplayName("Should not find an existing order when order reference does not match")
  public void shouldNotFindExistingCustomerOrder() {
    //given
    OrderReference orderReference = newOrderReference(newSubmissionId("abc"));
    when(brickOrderRepository.findBrickOrderByReference("abc")).thenReturn(Optional.empty());

    //when
    Optional<CustomerOrderDetail> actual = brickOrderingService.findOrder(orderReference);

    //then
    assertThat(actual.isPresent(), is(false));
  }

  @Test
  @DisplayName("Should find all existing orders")
  public void shouldFindAllExistingCustomerOrders() {
    //given
    BrickOrder brickOrder1 = BrickOrder.builder()
      .reference("abc")
      .quantity(10)
      .build();
    when(brickOrderRepository.findAll()).thenReturn(List.of(brickOrder1));

    //when
    List<CustomerOrderDetail> actual = brickOrderingService.findOrders();

    //then
    assertThat(actual.size(), is(1));
    assertThat(actual, contains(
      customerOrderDetailBuilder()
        .orderReference(newOrderReference(newSubmissionId("abc")))
        .orderQuantity(newOrderQuantity(10))
        .build()
    ));
  }
}
