package com.example.cart.domain;

import com.example.cart.domain.delivery.DeliveryService;
import com.example.cart.domain.discount.campaign.Campaign;
import com.example.cart.domain.discount.campaign.CampaignService;
import com.example.cart.domain.discount.coupon.Coupon;
import com.example.cart.domain.discount.coupon.CouponService;
import com.example.cart.domain.discount.coupon.exception.CouponNotExist;
import com.example.cart.domain.product.Category;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShoppingCartCalculator {

  private final CampaignService campaignService;
  private final CouponService couponService;
  private final DeliveryService deliveryService;
  private final ShoppingCartPrinter shoppingCartPrinter;

  public ShoppingCartCalculator(CampaignService campaignService,
                                CouponService couponService,
                                DeliveryService deliveryService,
                                ShoppingCartPrinter shoppingCartPrinter) {
    this.campaignService = campaignService;
    this.couponService = couponService;
    this.deliveryService = deliveryService;
    this.shoppingCartPrinter = shoppingCartPrinter;
  }

  public ShoppingCartCalculation calculateTotal(ShoppingCart shoppingCart) {
    applyCampaigns(shoppingCart);

    if (shoppingCart.getCouponId() != null) {
      applyCoupon(shoppingCart);
    }

    BigDecimal deliveryCost = deliveryService.calculateDeliveryCost(shoppingCart);
    ShoppingCartCalculation calculation = ShoppingCartCalculation.create(
        shoppingCart.calculateTotalAmount(),
        shoppingCart.calculateTotalAmountAfterDiscountsApplied(),
        shoppingCart.getCampaignDiscount(),
        shoppingCart.getCouponDiscount(),
        deliveryCost
    );

    shoppingCartPrinter.print(shoppingCart.getCartItems(), calculation);
    return calculation;
  }

  private void applyCampaigns(ShoppingCart shoppingCart) {
    List<Long> categoryIdList = shoppingCart.distinctCategories().stream()
        .map(Category::getId)
        .collect(Collectors.toList());

    List<Campaign> campaigns = campaignService.retrieveCampaignsForCategories(categoryIdList);
    shoppingCart.applyCampaign(campaigns);
  }

  private void applyCoupon(ShoppingCart shoppingCart) {
    Optional<Coupon> coupon = couponService.retrieveCoupon(shoppingCart.getCouponId());
    if (!coupon.isPresent()) {
      throw new CouponNotExist();
    }
    shoppingCart.applyCoupon(coupon.get());
  }
}
