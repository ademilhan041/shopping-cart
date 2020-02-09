package com.example.cart.domain;

import com.example.cart.domain.discount.Discount;
import com.example.cart.domain.discount.DiscountType;
import com.example.cart.domain.discount.campaign.Campaign;
import com.example.cart.domain.discount.coupon.Coupon;
import com.example.cart.domain.product.Category;
import com.example.cart.domain.product.Product;

import java.math.BigDecimal;

public class Data {

  public static Category category() {
    return category(1L);
  }

  public static Category category(Long id) {
    return Category.create(id, "T-Shirt");
  }

  public static Product product() {
    return product(1L, new BigDecimal(100));
  }

  public static Product product(Long productId, BigDecimal price) {
    return Product.create(productId, "T-1", price, category());
  }

  public static Product product(Long productId, BigDecimal price, Category category) {
    return Product.create(productId, "T-1", price, category);
  }

  public static Campaign campaign() {
    return Campaign.create(1L, 1L, 1, Discount.create(DiscountType.Rate, new BigDecimal(25)));
  }

  public static Coupon coupon() {
    return Coupon.create(1L, new BigDecimal(1), Discount.create(DiscountType.Rate, new BigDecimal(25)));
  }
}
