package com.trendyol.cart.domain.delivery;

import com.trendyol.cart.domain.ShoppingCart;

import java.math.BigDecimal;

public class DeliveryService {

  private final BigDecimal COST_PER_DELIVERY = new BigDecimal("10.0");
  private final BigDecimal COST_PER_PRODUCT = new BigDecimal("2.0");
  private final BigDecimal FIXED_COST = new BigDecimal("2.99");

  private final DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(
      COST_PER_DELIVERY,
      COST_PER_PRODUCT,
      FIXED_COST);

  public BigDecimal calculateFor(ShoppingCart cart) {
    return deliveryCostCalculator.calculateFor(cart);
  }
}
