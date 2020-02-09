package com.example.cart.domain.discount;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Discount {

  private final DiscountType discountType;
  private final BigDecimal discountValue;

  public static final Map<DiscountType, DiscountCalculator> discountCalculatorsMap = new HashMap<>();

  static {
    discountCalculatorsMap.put(DiscountType.Amount, new DiscountAmountCalculator());
    discountCalculatorsMap.put(DiscountType.Rate, new DiscountRateCalculator());
  }

  private Discount(DiscountType discountType, BigDecimal discountValue) {
    this.discountType = discountType;
    this.discountValue = discountValue;
  }

  public static Discount create(DiscountType discountType, BigDecimal discountValue) {
    return new Discount(discountType, discountValue);
  }

  public DiscountType getDiscountType() {
    return discountType;
  }

  public BigDecimal getDiscountValue() {
    return discountValue;
  }

  public BigDecimal calculate(BigDecimal amount) {
    return discountCalculatorsMap.get(discountType).calculate(amount, discountValue);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Discount discount = (Discount) o;
    return discountValue.equals(discount.discountValue) &&
        discountType == discount.discountType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(discountValue, discountType);
  }

  @Override
  public String toString() {
    return "Discount{" +
        "discountValue=" + discountValue +
        ", discountType=" + discountType +
        '}';
  }
}
