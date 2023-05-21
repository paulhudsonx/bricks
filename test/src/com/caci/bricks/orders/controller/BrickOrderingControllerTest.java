package com.caci.bricks.orders.controller;

import static io.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BrickOrderingControllerTest {

  @LocalServerPort
  private int serverPort;

  @BeforeEach
  public void setup() {
    RestAssured.port = serverPort;
  }

  @Test
  @DisplayName("Given a valid customer, should start a brick order and return an order reference")
  void startOrderReturnsAnOrderReference() {
    with()
      .given().log().all()
      .body(InitialCustomerOrderRequest.of("Customer123", 0))
      .contentType(ContentType.JSON)
      .when()
      .request("POST", "/bricks/start-order")
      .then().log().body()
      .statusCode(HttpStatus.CREATED.value())
      .body("submissionId", notNullValue());
  }

  @Test
  @DisplayName("Should return not found if no customer order exists for an order reference")
  void findOrderDetailsReturnsNotFound() {
    with()
      .given().log().all()
      .when()
      .request("GET", "/bricks/find-order/11111111")
      .then()
      .log().body()
      .assertThat()
      .statusCode(404);
  }

  @Test
  @DisplayName("Should return order details if customer order exists for an order reference")
  void findOrderDetailsReturnsFound() {
    // Create an order
    OrderReferenceResponse existingOrder = with()
      .body(InitialCustomerOrderRequest.of("Customer123", 10))
      .contentType(ContentType.JSON)
      .request("POST", "/bricks/start-order")
      .as(OrderReferenceResponse.class);

    // Verify order
    with()
      .given().log().all()
      .when()
      .request("GET", "/bricks/find-order/" + existingOrder.getSubmissionId())
      .then()
      .log().body()
      .assertThat()
      .statusCode(200)
      .body("orderReference", is(existingOrder.getSubmissionId()))
      .body("quantity", is(10));
  }
}