//package com.trendyol.cart;
//
//import com.trendyol.cart.domain.ShoppingCart;
//import com.trendyol.cart.domain.discount.Discount;
//import com.trendyol.cart.domain.discount.DiscountType;
//import com.trendyol.cart.domain.discount.campaign.Campaign;
//import com.trendyol.cart.domain.discount.coupon.Coupon;
//import com.trendyol.cart.domain.product.Category;
//import com.trendyol.cart.domain.product.Product;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//
//public class Main {
//
//  public static void main(String[] args) {
//    Category category1 = Category.create(1L, "T-Shirt");
//    Category category2 = Category.create(2L, "Sleeve");
//    Category category3 = Category.create(3L, "Hoodie");
//
//    Product product1 = Product.create(1L, "T-1", new BigDecimal(100), category1);
//    Product product2 = Product.create(2L, "T-2", new BigDecimal(200), category1);
//    Product product3 = Product.create(3L, "T-3", new BigDecimal(300), category1);
//
//    Product product4 = Product.create(4L, "S-1", new BigDecimal(500), category2);
//    Product product5 = Product.create(5L, "S-2", new BigDecimal(800), category2);
//    Product product6 = Product.create(6L, "S-3", new BigDecimal(300), category2);
//
//    Product product7 = Product.create(7L, "H-1", new BigDecimal(1200), category3);
//    Product product8 = Product.create(8L, "H-2", new BigDecimal(850), category3);
//    Product product9 = Product.create(9L, "H-3", new BigDecimal(190), category3);
//
//    Campaign campaign1 = Campaign.create(1L, category1, 2, Discount.create(DiscountType.Rate, new BigDecimal(25)));
//    Campaign campaign2 = Campaign.create(2L, category2, 2, Discount.create(DiscountType.Amount, new BigDecimal(100)));
//    Campaign campaign3 = Campaign.create(3L, category2, 4, Discount.create(DiscountType.Amount, new BigDecimal(100)));
//    Campaign campaign4 = Campaign.create(3L, category3, 3, Discount.create(DiscountType.Rate, new BigDecimal(25)));
//
//    Coupon coupon1 = Coupon.create(1L, new BigDecimal(1000), Discount.create(DiscountType.Rate, new BigDecimal(25)));
//
//    ShoppingCart cart = new ShoppingCart();
//    cart.addItem(product1, 1);
//    cart.addItem(product3, 3);
//    cart.addItem(product4, 2);
//
//    cart.applyCampaign(Arrays.asList(campaign1, campaign2));
//    cart.applyCoupon(coupon1);
//
//  }
//}
