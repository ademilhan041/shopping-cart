package com.trendyol.cart.infrastructure.client;

import com.trendyol.cart.domain.discount.coupon.Coupon;
import com.trendyol.cart.domain.discount.coupon.CouponService;

import java.util.Optional;

public class CouponMockService implements CouponService {

  @Override
  public Optional<Coupon> retrieveCoupon(Long couponId) {
    return Optional.empty();
  }
}
