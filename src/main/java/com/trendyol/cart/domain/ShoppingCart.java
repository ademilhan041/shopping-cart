package com.trendyol.cart.domain;

import com.trendyol.cart.domain.discount.campaign.Campaign;
import com.trendyol.cart.domain.discount.coupon.Coupon;
import com.trendyol.cart.domain.product.Category;
import com.trendyol.cart.domain.product.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class ShoppingCart {

  private List<ShoppingCartItem> cartItems = new ArrayList<>();
  private Long couponId;

  private BigDecimal campaignDiscount = BigDecimal.ZERO;
  private BigDecimal couponDiscount = BigDecimal.ZERO;

  public ShoppingCart(List<ShoppingCartItem> cartItems, Long couponId) {
    this.cartItems = cartItems;
    this.couponId = couponId;
  }

  public static ShoppingCart create(List<ShoppingCartItem> cartItems, Long couponId) {
    return new ShoppingCart(cartItems, couponId);
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
      item.get().incrementQuantityBy(quantity);
    } else {
      cartItems.add(new ShoppingCartItem(product, quantity));
    }
  }

  public void removeItem(Product product) {
    Optional<ShoppingCartItem> item = findItemByProduct(product);
    item.map(shoppingCartItem -> cartItems.remove(shoppingCartItem));
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

  public BigDecimal calculateTotalAmountForCategory(Category category) {
    return cartItems.stream()
        .filter(item -> item.getCategoryOfProduct().equals(category))
        .map(ShoppingCartItem::getItemTotalAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public BigDecimal calculateTotalAmountAfterDiscounts() {
    return calculateTotalAmount().subtract(campaignDiscount).subtract(couponDiscount);
  }

  public void applyCampaign(List<Campaign> campaigns) {
    Map<Category, List<Campaign>> campaignsByCategory = campaigns.stream()
        .filter(campaign -> campaign.isApplicable(countProductForCategory(campaign.getCategory())))
        .collect(groupingBy(Campaign::getCategory));

    List<BigDecimal> categoryDiscounts = new ArrayList<>();
    campaignsByCategory
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

  private Integer countProductForCategory(Category category) {
    return cartItems.stream()
        .filter(item -> category.equals(item.getCategoryOfProduct()))
        .map(ShoppingCartItem::getQuantity)
        .reduce(0, Integer::sum);
  }

  private BigDecimal findCampaignMaxDiscount(List<Campaign> campaigns) {
    return campaigns.stream()
        .map(campaign -> campaign.getDiscount().calculate(calculateTotalAmountForCategory(campaign.getCategory())))
        .max(BigDecimal::compareTo)
        .get();
  }
}
