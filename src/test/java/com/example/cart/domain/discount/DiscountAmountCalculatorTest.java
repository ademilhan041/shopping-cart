package com.example.cart.domain.discount;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountAmountCalculatorTest {

  private DiscountAmountCalculator calculator;

  @Before
  public void setUp() {
    calculator = new DiscountAmountCalculator();
  }

  @Test
  public void should_calculate_by_amount() {
    // when
    BigDecimal discountedAmount = calculator.calculate(new BigDecimal("100.00"), new BigDecimal("50.00"));

    // then
    assertThat(discountedAmount).isEqualTo(new BigDecimal("50.00"));
  }
}
