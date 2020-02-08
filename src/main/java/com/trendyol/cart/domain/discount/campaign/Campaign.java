package com.trendyol.cart.domain.discount.campaign;

import com.trendyol.cart.domain.discount.Discount;
import com.trendyol.cart.domain.product.Category;

import java.util.Objects;

public class Campaign {

  private final Long id;
  private final Category category;
  private final int minProductRequired;
  private final Discount discount;

  public Campaign(Long id, Category category, int minProductRequired, Discount discount) {
    this.id = id;
    this.category = category;
    this.minProductRequired = minProductRequired;
    this.discount = discount;
  }

  public Long getId() {
    return id;
  }

  public Category getCategory() {
    return category;
  }

  public int getMinProductRequired() {
    return minProductRequired;
  }

  public Discount getDiscount() {
    return discount;
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
        ", category=" + category +
        ", minProductRequired=" + minProductRequired +
        ", discount=" + discount +
        '}';
  }
}
