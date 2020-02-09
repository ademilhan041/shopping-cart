package com.example.cart.domain;

import com.example.cart.domain.discount.campaign.Campaign;
import com.example.cart.domain.discount.coupon.Coupon;
import com.example.cart.domain.product.Category;
import com.example.cart.domain.product.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class ShoppingCart {

  private List<ShoppingCartItem> cartItems = new ArrayList<>();
  private Long couponId;

  private BigDecimal campaignDiscount = BigDecimal.ZERO;
  private BigDecimal couponDiscount = BigDecimal.ZERO;

  public static ShoppingCart createEmptyCart() {
    return new ShoppingCart();
  }

  public static ShoppingCart createCart(ShoppingCartItem... items) {
    ShoppingCart cart = new ShoppingCart();
    if (items != null) {
      cart.cartItems = Arrays.stream(items).collect(Collectors.toList());
    }
    return cart;
  }

  public static ShoppingCart createWithCoupon(Long couponId) {
    ShoppingCart cart = new ShoppingCart();
    cart.couponId = couponId;
    return cart;
  }

  public List<ShoppingCartItem> getCartItems() {
    return cartItems;
  }

  public Long getCouponId() {
    return couponId;
  }

  public BigDecimal getCampaignDiscount() {
    return campaignDiscount;
  }

  public BigDecimal getCouponDiscount() {
    return couponDiscount;
  }

  public void addItem(Product product, int quantity) {
    Optional<ShoppingCartItem> item = findItemByProduct(product);

    if (item.isPresent()) {
      int index = cartItems.indexOf(item.get());
      cartItems.set(index, item.get().incrementQuantityBy(quantity));
    } else {
      cartItems.add(ShoppingCartItem.create(product, quantity));
    }
  }

  public Optional<ShoppingCartItem> findItemByProduct(Product product) {
    return cartItems.stream()
        .filter(item -> item.getProduct().equals(product))
        .findFirst();
  }

  public BigDecimal calculateTotalAmount() {
    return cartItems.stream()
        .map(ShoppingCartItem::getItemTotalAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public BigDecimal calculateTotalAmountForCategory(Long categoryId) {
    return cartItems.stream()
        .filter(item -> item.getCategoryOfProduct().getId().equals(categoryId))
        .map(ShoppingCartItem::getItemTotalAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public BigDecimal calculateTotalAmountAfterDiscountsApplied() {
    return calculateTotalAmount().subtract(campaignDiscount).subtract(couponDiscount);
  }

  public void applyCampaign(List<Campaign> campaigns) {
    Map<Long, List<Campaign>> applicableCampaignsByCategory = campaigns.stream()
        .filter(campaign -> campaign.isApplicable(countProductForCategory(campaign.getCategoryId())))
        .collect(groupingBy(Campaign::getCategoryId));

    List<BigDecimal> categoryDiscounts = new ArrayList<>();
    applicableCampaignsByCategory
        .forEach((category, campaignsOfCategory) -> {
          BigDecimal maxDiscountOfCategory = findCampaignMaxDiscount(campaignsOfCategory);
          categoryDiscounts.add(maxDiscountOfCategory);
        });

    this.campaignDiscount = categoryDiscounts.stream()
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public void applyCoupon(Coupon coupon) {
    BigDecimal cartAmount = calculateTotalAmount().subtract(campaignDiscount);
    if (coupon.isApplicable(cartAmount)) {
      this.couponDiscount = coupon.getDiscount().calculate(cartAmount);
    }
  }

  public List<Category> distinctCategories() {
    return cartItems.stream()
        .map(ShoppingCartItem::getCategoryOfProduct)
        .distinct()
        .collect(Collectors.toList());
  }

  private Integer countProductForCategory(Long categoryId) {
    return cartItems.stream()
        .filter(item -> categoryId.equals(item.getCategoryOfProduct().getId()))
        .map(ShoppingCartItem::getQuantity)
        .reduce(0, Integer::sum);
  }

  private BigDecimal findCampaignMaxDiscount(List<Campaign> campaigns) {
    return campaigns.stream()
        .map(campaign -> campaign.getDiscount().calculate(calculateTotalAmountForCategory(campaign.getCategoryId())))
        .max(BigDecimal::compareTo)
        .get();
  }
}
