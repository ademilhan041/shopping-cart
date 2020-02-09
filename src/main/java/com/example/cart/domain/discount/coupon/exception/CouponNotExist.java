package com.example.cart.domain.discount.coupon.exception;

public class CouponNotExist extends RuntimeException {

  public CouponNotExist() {
    super("COUPON_NOT_EXIST");
  }
}
