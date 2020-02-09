package com.example.cart.domain;

import com.example.cart.domain.product.Category;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class ShoppingCartPrinter {

  public String print(List<ShoppingCartItem> cartItems, ShoppingCartCalculation cartCalculation) {
    Map<Category, List<ShoppingCartItem>> itemsByCategory = cartItems.stream().collect(groupingBy(ShoppingCartItem::getCategoryOfProduct));

    StringBuilder sb = new StringBuilder();
    itemsByCategory.forEach((category, items) -> {
      for (ShoppingCartItem item : items) {
        sb.append(item.getCategoryOfProduct().getTitle()).append("\t\t")
            .append(item.getProduct().getTitle()).append("\t\t")
            .append(item.getQuantity()).append("\t\t")
            .append(item.getProduct().getPrice()).append("\t\t")
            .append(item.getItemTotalAmount())
            .append("\n");
      }
    });

    sb.append("\n")
        .append("Total Amount before All Discounts: ").append(cartCalculation.getTotalAmountBeforeDiscount()).append("\n")
        .append("Discount by Campaign: ").append(cartCalculation.getCampaignDiscount()).append("\n")
        .append("Discount by Coupon: ").append(cartCalculation.getCouponDiscount()).append("\n")
        .append("Total Amount after All Discounts: ").append(cartCalculation.getTotalAmountAfterDiscount()).append("\n")
        .append("Delivery Cost: ").append(cartCalculation.getDeliveryCost());
    String content = sb.toString();
    System.out.println(content);
    return content;
  }
}
