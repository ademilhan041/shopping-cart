package com.example.cart;

import com.example.cart.domain.ShoppingCart;
import com.example.cart.domain.ShoppingCartCalculator;
import com.example.cart.domain.ShoppingCartPrinter;
import com.example.cart.domain.delivery.DeliveryPerCost;
import com.example.cart.domain.delivery.DeliveryCostCalculator;
import com.example.cart.domain.delivery.DeliveryService;
import com.example.cart.domain.discount.Discount;
import com.example.cart.domain.discount.DiscountType;
import com.example.cart.domain.discount.campaign.Campaign;
import com.example.cart.domain.discount.coupon.Coupon;
import com.example.cart.domain.product.Category;
import com.example.cart.domain.product.Product;
import com.example.cart.infrastructure.client.CampaignFakeService;
import com.example.cart.infrastructure.client.CouponFakeService;

import java.math.BigDecimal;

public class Main {

  private CampaignFakeService campaignService = new CampaignFakeService();
  private CouponFakeService couponService = new CouponFakeService();
  private DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(DeliveryPerCost.PER_DELIVERY, DeliveryPerCost.PER_PRODUCT, DeliveryPerCost.FIXED_COST);
  private DeliveryService deliveryService = new DeliveryService(deliveryCostCalculator);
  private ShoppingCartPrinter shoppingCartPrinter = new ShoppingCartPrinter();

  private ShoppingCartCalculator shoppingCartCalculator = new ShoppingCartCalculator(
      campaignService,
      couponService,
      deliveryService,
      shoppingCartPrinter);

  public static void main(String[] args) {
    Main main = new Main();
    main.initData();
    main.test();
  }

  private void initData() {
    campaignService.createCampaign(Campaign.create(1L, 1L, 2, Discount.create(DiscountType.Rate, new BigDecimal(10))));
    campaignService.createCampaign(Campaign.create(2L, 2L, 4, Discount.create(DiscountType.Rate, new BigDecimal(20))));
    campaignService.createCampaign(Campaign.create(3L, 3L, 3, Discount.create(DiscountType.Rate, new BigDecimal(25))));

    couponService.createCoupon(Coupon.create(1L, new BigDecimal(1000), Discount.create(DiscountType.Amount, new BigDecimal(50))));
  }

  private void test() {
    Category category1 = Category.create(1L, "T-Shirt");
    Category category2 = Category.create(2L, "Sleeve");
    Category category3 = Category.create(3L, "Hoodie");

    Product product1 = Product.create(1L, "T-1", new BigDecimal(100), category1);
    Product product2 = Product.create(2L, "T-2", new BigDecimal(200), category1);
    Product product3 = Product.create(3L, "T-3", new BigDecimal(300), category1);

    Product product4 = Product.create(4L, "S-1", new BigDecimal(500), category2);
    Product product5 = Product.create(5L, "S-2", new BigDecimal(800), category2);
    Product product6 = Product.create(6L, "S-3", new BigDecimal(300), category2);

    Product product7 = Product.create(7L, "H-1", new BigDecimal(1200), category3);
    Product product8 = Product.create(8L, "H-2", new BigDecimal(850), category3);
    Product product9 = Product.create(9L, "H-3", new BigDecimal(190), category3);

    ShoppingCart shoppingCart = ShoppingCart.createWithCoupon(1L);
    shoppingCart.addItem(product1, 1);
    shoppingCart.addItem(product3, 3);
    shoppingCart.addItem(product4, 2);

    shoppingCartCalculator.calculateTotal(shoppingCart);
  }
}
