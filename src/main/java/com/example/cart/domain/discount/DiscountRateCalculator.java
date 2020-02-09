package com.example.cart.domain.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountRateCalculator implements DiscountCalculator {

  @Override
  public BigDecimal calculate(BigDecimal amount, BigDecimal discountValue) {
    return amount
        .multiply(discountValue)
        .divide(new BigDecimal(100), RoundingMode.HALF_UP)
        .setScale(2, RoundingMode.HALF_UP);
  }
}
