package com.trendyol.cart.domain.delivery;

import com.trendyol.cart.domain.ShoppingCart;

import java.math.BigDecimal;

public class DeliveryCostCalculator {

  private final BigDecimal costPerDelivery;
  private final BigDecimal costPerProduct;
  private final BigDecimal fixedCost;

  public DeliveryCostCalculator(BigDecimal costPerDelivery, BigDecimal costPerProduct, BigDecimal fixedCost) {
    this.costPerDelivery = costPerDelivery;
    this.costPerProduct = costPerProduct;
    this.fixedCost = fixedCost;
  }

  public BigDecimal calculateFor(ShoppingCart cart) {
    int numberOfDeliveries = cart.distinctCategories().size();
    int numberOfProducts = cart.getCartItems().size();

    BigDecimal totalPerDelivery = costPerDelivery.multiply(new BigDecimal(numberOfDeliveries));
    BigDecimal totalPerProducts = costPerProduct.multiply(new BigDecimal(numberOfProducts));
    return totalPerDelivery.add(totalPerProducts).add(fixedCost);
  }
}
