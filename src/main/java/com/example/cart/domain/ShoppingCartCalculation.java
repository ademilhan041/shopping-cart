package com.example.cart.domain;

import java.math.BigDecimal;

public class ShoppingCartCalculation {

  private final BigDecimal totalAmountBeforeDiscount;
  private final BigDecimal totalAmountAfterDiscount;
  private final BigDecimal campaignDiscount;
  private final BigDecimal couponDiscount;
  private final BigDecimal deliveryCost;

  private ShoppingCartCalculation(BigDecimal totalAmountBeforeDiscount,
                                  BigDecimal totalAmountAfterDiscount,
                                  BigDecimal campaignDiscount,
                                  BigDecimal couponDiscount,
                                  BigDecimal deliveryCost) {
    this.totalAmountBeforeDiscount = totalAmountBeforeDiscount;
    this.totalAmountAfterDiscount = totalAmountAfterDiscount;
    this.campaignDiscount = campaignDiscount;
    this.couponDiscount = couponDiscount;
    this.deliveryCost = deliveryCost;
  }

  public static ShoppingCartCalculation create(BigDecimal totalAmountBeforeDiscount,
                                               BigDecimal totalAmountAfterDiscount,
                                               BigDecimal campaignDiscount,
                                               BigDecimal couponDiscount,
                                               BigDecimal deliveryCost) {
    return new ShoppingCartCalculation(totalAmountBeforeDiscount, totalAmountAfterDiscount, campaignDiscount,
        couponDiscount, deliveryCost);
  }

  public BigDecimal getTotalAmountBeforeDiscount() {
    return totalAmountBeforeDiscount;
  }

  public BigDecimal getTotalAmountAfterDiscount() {
    return totalAmountAfterDiscount;
  }

  public BigDecimal getCampaignDiscount() {
    return campaignDiscount;
  }

  public BigDecimal getCouponDiscount() {
    return couponDiscount;
  }

  public BigDecimal getDeliveryCost() {
    return deliveryCost;
  }

  @Override
  public String toString() {
    return "ShoppingCartCalculation{" +
        "totalAmountBeforeDiscount=" + totalAmountBeforeDiscount +
        ", totalAmountAfterDiscount=" + totalAmountAfterDiscount +
        ", campaignDiscount=" + campaignDiscount +
        ", couponDiscount=" + couponDiscount +
        ", deliveryCost=" + deliveryCost +
        '}';
  }
}
