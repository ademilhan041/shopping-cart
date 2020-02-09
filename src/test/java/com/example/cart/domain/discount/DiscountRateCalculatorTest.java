package com.example.cart.domain.discount;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountRateCalculatorTest {

  private DiscountRateCalculator calculator;

  @Before
  public void setUp() {
    calculator = new DiscountRateCalculator();
  }

  @Test
  public void should_calculate_by_rate() {
    // when
    BigDecimal discountedAmount = calculator.calculate(new BigDecimal("100.00"), new BigDecimal("25.00"));

    // then
    assertThat(discountedAmount).isEqualTo(new BigDecimal("25.00"));
  }
}
