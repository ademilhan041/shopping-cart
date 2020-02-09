package com.example.cart.domain.discount;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountTest {

  @Test
  public void should_calculate_by_rate_when_discount_type_is_rate() {
    // given
    Discount discount = Discount.create(DiscountType.Rate, new BigDecimal("25.00"));

    // when
    BigDecimal discountedAmount = discount.calculate(new BigDecimal("100.00"));

    // then
    assertThat(discountedAmount).isEqualTo(new BigDecimal("25.00"));
  }

  @Test
  public void should_calculate_by_amount_when_discount_type_is_amount() {
    // given
    Discount discount = Discount.create(DiscountType.Amount, new BigDecimal("75.00"));

    // when
    BigDecimal discountedAmount = discount.calculate(new BigDecimal("200.00"));

    // then
    assertThat(discountedAmount).isEqualTo(new BigDecimal("75.00"));
  }
}
