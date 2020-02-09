package com.example.cart.domain;

import com.example.cart.domain.product.Category;
import com.example.cart.domain.product.Product;

import java.math.BigDecimal;

public class ShoppingCartItem {

  private final Product product;
  private final int quantity;

  private ShoppingCartItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public static ShoppingCartItem create(Product product, int quantity) {
    return new ShoppingCartItem(product, quantity);
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

  public ShoppingCartItem incrementQuantityBy(int increment) {
    return create(product, this.quantity + increment);
  }

  public Category getCategoryOfProduct() {
    return product.getCategory();
  }

  public BigDecimal getItemTotalAmount() {
    return product.getPrice().multiply(new BigDecimal(quantity));
  }

  @Override
  public String toString() {
    return "ShoppingCartItem{" +
        "product=" + product +
        ", quantity=" + quantity +
        '}';
  }
}
