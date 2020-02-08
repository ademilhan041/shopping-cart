package com.trendyol.cart.domain.discount.coupon;

import java.util.Optional;

public interface CouponService {

  Optional<Coupon> retrieveCoupon(Long couponId);
}
