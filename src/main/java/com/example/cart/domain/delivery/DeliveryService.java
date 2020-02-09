package com.example.cart.domain.delivery;

import com.example.cart.domain.ShoppingCart;

import java.math.BigDecimal;

public class DeliveryService {

  private final DeliveryCostCalculator deliveryCostCalculator;

  public DeliveryService(DeliveryCostCalculator deliveryCostCalculator) {
    this.deliveryCostCalculator = deliveryCostCalculator;
  }

  public BigDecimal calculateDeliveryCost(ShoppingCart cart) {
    return deliveryCostCalculator.calculateDeliveryCost(cart);
  }
}
