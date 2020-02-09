package com.example.cart.domain.delivery;

import com.example.cart.domain.ShoppingCart;

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

  public BigDecimal calculateDeliveryCost(ShoppingCart cart) {
    BigDecimal totalPerDelivery = calculateTotalPerDelivery(cart);
    BigDecimal totalPerProducts = calculateTotalPerProducts(cart);

    return totalPerDelivery.add(totalPerProducts).add(fixedCost);
  }

  public BigDecimal calculateTotalPerDelivery(ShoppingCart cart) {
    int numberOfDeliveries = cart.distinctCategories().size();
    return costPerDelivery.multiply(new BigDecimal(numberOfDeliveries));
  }

  public BigDecimal calculateTotalPerProducts(ShoppingCart cart) {
    int numberOfProducts = cart.getCartItems().size();
    return costPerProduct.multiply(new BigDecimal(numberOfProducts));
  }
}
