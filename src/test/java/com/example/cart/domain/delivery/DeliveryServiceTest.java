package com.example.cart.domain.delivery;

import com.example.cart.domain.ShoppingCart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryServiceTest {

  private DeliveryService deliveryService;

  @Mock
  private DeliveryCostCalculator deliveryCostCalculator;

  @Before
  public void setUp() {
    deliveryService = new DeliveryService(deliveryCostCalculator);
  }

  @Test
  public void should_calculate_delivery_cost_by_invoking_calculator() {
    // given
    ShoppingCart cart = ShoppingCart.createEmptyCart();

    // when
    deliveryService.calculateDeliveryCost(cart);

    // then
    verify(deliveryCostCalculator).calculateDeliveryCost(cart);
  }
}
