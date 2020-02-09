package com.example.cart.infrastructure.client;

import com.example.cart.domain.discount.coupon.Coupon;
import com.example.cart.domain.discount.coupon.CouponService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CouponFakeService implements CouponService {

  private List<Coupon> coupons = new ArrayList<>();

  // For test data
  public void createCoupon(Coupon coupon) {
    coupons.add(coupon);
  }

  @Override
  public Optional<Coupon> retrieveCoupon(Long couponId) {
    return coupons.stream()
        .filter(c -> c.getId().equals(couponId))
        .findFirst();
  }
}
