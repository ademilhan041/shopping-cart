package com.example.cart.domain.discount.campaign;

import com.example.cart.domain.discount.Discount;

import java.util.Objects;

public class Campaign {

  private final Long id;
  private final Long categoryId;
  private final int minProductRequired;
  private final Discount discount;

  private Campaign(Long id, Long categoryId, int minProductRequired, Discount discount) {
    this.id = id;
    this.categoryId = categoryId;
    this.minProductRequired = minProductRequired;
    this.discount = discount;
  }

  public static Campaign create(Long id, Long categoryId, int minProductRequired, Discount discount) {
    return new Campaign(id, categoryId, minProductRequired, discount);
  }

  public Long getId() {
    return id;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public int getMinProductRequired() {
    return minProductRequired;
  }

  public Discount getDiscount() {
    return discount;
  }

  public boolean isApplicable(int productCountInCategory) {
    return productCountInCategory >= minProductRequired;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Campaign campaign = (Campaign) o;
    return Objects.equals(id, campaign.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Campaign{" +
        "id=" + id +
        ", categoryId=" + categoryId +
        ", minProductRequired=" + minProductRequired +
        ", discount=" + discount +
        '}';
  }
}
