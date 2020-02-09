package com.example.cart.domain.discount.coupon;

import com.example.cart.domain.discount.Discount;

import java.math.BigDecimal;
import java.util.Objects;

public class Coupon {

  private final Long id;
  private final BigDecimal minCartTotalAmount;
  private final Discount discount;

  private Coupon(Long id, BigDecimal minCartTotalAmount, Discount discount) {
    this.id = id;
    this.minCartTotalAmount = minCartTotalAmount;
    this.discount = discount;
  }

  public static Coupon create(Long id, BigDecimal minCartTotalAmount, Discount discount) {
    return new Coupon(id, minCartTotalAmount, discount);
  }

  public Long getId() {
    return id;
  }

  public BigDecimal getMinCartTotalAmount() {
    return minCartTotalAmount;
  }

  public Discount getDiscount() {
    return discount;
  }

  public boolean isApplicable(BigDecimal cartPurchaseAmount) {
    return cartPurchaseAmount.compareTo(minCartTotalAmount) > -1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coupon coupon = (Coupon) o;
    return Objects.equals(id, coupon.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Coupon{" +
        "id=" + id +
        ", minPurchaseAmount=" + minCartTotalAmount +
        ", discount=" + discount +
        '}';
  }
}
