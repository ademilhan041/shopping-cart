package com.trendyol.cart.domain.calculation;

import com.trendyol.cart.domain.ShoppingCart;
import com.trendyol.cart.domain.ShoppingCartPrinter;
import com.trendyol.cart.domain.delivery.DeliveryService;
import com.trendyol.cart.domain.discount.campaign.Campaign;
import com.trendyol.cart.domain.discount.campaign.CampaignService;
import com.trendyol.cart.domain.discount.coupon.Coupon;
import com.trendyol.cart.domain.discount.coupon.CouponService;
import com.trendyol.cart.domain.product.Category;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShoppingCartCalculationService {

  private final CampaignService campaignService;
  private final CouponService couponService;
  private final DeliveryService deliveryService;
  private final ShoppingCartPrinter shoppingCartPrinter;

  public ShoppingCartCalculationService(CampaignService campaignService,
                                        CouponService couponService,
                                        DeliveryService deliveryService,
                                        ShoppingCartPrinter shoppingCartPrinter) {
    this.campaignService = campaignService;
    this.couponService = couponService;
    this.deliveryService = deliveryService;
    this.shoppingCartPrinter = shoppingCartPrinter;
  }

  public ShoppingCartCalculation calculateTotals(ShoppingCart shoppingCart) {
    List<Long> categoryIdList = shoppingCart.distinctCategories().stream()
        .map(Category::getId)
        .collect(Collectors.toList());
    List<Campaign> campaigns = campaignService.retrieveCampaignsForCategories(categoryIdList);
    Optional<Coupon> coupon = couponService.retrieveCoupon(shoppingCart.getCouponId());

    shoppingCart.applyCampaign(campaigns);
    coupon.ifPresent(shoppingCart::applyCoupon);

    BigDecimal deliveryCost = deliveryService.calculateFor(shoppingCart);
    ShoppingCartCalculation calculation = new ShoppingCartCalculation(shoppingCart.calculateTotalAmount(),
        shoppingCart.calculateTotalAmountAfterDiscounts(),
        shoppingCart.getCampaignDiscount(),
        shoppingCart.getCampaignDiscount(),
        deliveryCost
    );

    shoppingCartPrinter.prettyString(shoppingCart.getCartItems(), calculation);

    return calculation;
  }
}
