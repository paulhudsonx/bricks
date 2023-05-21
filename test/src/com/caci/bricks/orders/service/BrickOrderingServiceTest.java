package com.caci.bricks.orders.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    CustomerOrder customerOrder = CustomerOrderBuilder.newBuilder()
      .withOrderQuantity(OrderQuantity.of(1))
      .build();

    when(submissionIdObjectFactory.getObject()).thenReturn(SubmissionId.of("bar"));

    //when
    OrderReference actual = brickOrderingService.startCustomerOrder(customerOrder);

    //then
    assertThat(actual.getSubmissionId().getIdentifier(), is("bar"));
    verify(brickOrderRepository).save(any());
  }

  @Test
  @DisplayName("Given a valid customer order when the customer order started then a new order is saved")
  public void shouldSaveCustomerOrder() {
    //given
    CustomerOrder customerOrder = CustomerOrderBuilder.newBuilder()
      .withOrderQuantity(OrderQuantity.of(1))
      .build();
    when(submissionIdObjectFactory.getObject()).thenReturn(SubmissionId.of("bar"));

    //when
    brickOrderingService.startCustomerOrder(customerOrder);

    //then
    verify(brickOrderRepository).save(brickOrder.capture());
    assertThat(brickOrder.getValue().getQuantity(), is(1));
    assertThat(brickOrder.getValue().getReference(), is("bar"));
  }

  @Test
  @DisplayName("Should find an existing order when order reference matches")
  public void shouldFindExistingCustomerOrder() {
    //given
    OrderReference orderReference = OrderReference.of(SubmissionId.of("abc"));
    BrickOrder brickOrder1 = new BrickOrder();
    brickOrder1.setReference("abc");
    brickOrder1.setQuantity(10);
    when(brickOrderRepository.findBrickOrderByReference("abc")).thenReturn(Optional.of(brickOrder1));

    //when
    Optional<CustomerOrderDetail> actual = brickOrderingService.findOrder(orderReference);

    //then
    assertThat(actual.isPresent(), is(true));
    assertThat(actual.get().getOrderQuantity(), samePropertyValuesAs(OrderQuantity.of(10)));
    assertThat(actual.get().getOrderReference().getSubmissionId(), samePropertyValuesAs(SubmissionId.of("abc")));
  }

  @Test
  @DisplayName("Should not find an existing order when order reference does not match")
  public void shouldNotFindExistingCustomerOrder() {
    //given
    OrderReference orderReference = OrderReference.of(SubmissionId.of("abc"));
    when(brickOrderRepository.findBrickOrderByReference("abc")).thenReturn(Optional.empty());

    //when
    Optional<CustomerOrderDetail> actual = brickOrderingService.findOrder(orderReference);

    //then
    assertThat(actual.isPresent(), is(false));
  }
}
