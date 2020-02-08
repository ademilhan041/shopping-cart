package com.trendyol.cart.domain.discount;

import java.math.BigDecimal;

public class DiscountAmountCalculator implements DiscountCalculator {

  @Override
  public BigDecimal calculate(BigDecimal amount, BigDecimal discountValue) {
    return discountValue;
  }
}
