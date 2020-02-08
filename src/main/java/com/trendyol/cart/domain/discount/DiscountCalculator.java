package com.trendyol.cart.domain.discount;

import java.math.BigDecimal;

public interface DiscountCalculator {

  BigDecimal calculate(BigDecimal amount, BigDecimal discountValue);
}
