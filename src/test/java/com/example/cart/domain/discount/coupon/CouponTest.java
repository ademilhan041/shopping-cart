package com.example.cart.domain.discount.coupon;

import com.example.cart.domain.discount.Discount;
import com.example.cart.domain.discount.DiscountType;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CouponTest {

  @Test
  public void should_applicable_if_given_amount_is_greater_than_coupon_min_cart_total_amount() {
    // given
    Coupon coupon = Coupon.create(1L, new BigDecimal("500"), sampleDiscount());

    // when
    boolean isApplicable = coupon.isApplicable(new BigDecimal("600"));

    // then
    assertThat(isApplicable).isTrue();
  }

  @Test
  public void should_applicable_if_given_amount_is_equal_to_coupon_min_cart_total_amount() {
    // given
    Coupon coupon = Coupon.create(1L, new BigDecimal("500"), sampleDiscount());

    // when
    boolean isApplicable = coupon.isApplicable(new BigDecimal("500"));

    // then
    assertThat(isApplicable).isTrue();
  }

  @Test
  public void should_not_applicable_if_given_amount_is_less_than_coupon_min_cart_total_amount() {
    // given
    Coupon coupon = Coupon.create(1L, new BigDecimal("500"), sampleDiscount());

    // when
    boolean isApplicable = coupon.isApplicable(new BigDecimal("300"));

    // then
    assertThat(isApplicable).isFalse();
  }

  private Discount sampleDiscount() {
    return Discount.create(DiscountType.Rate, new BigDecimal("25"));
  }
}
