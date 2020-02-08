package com.trendyol.cart.domain;

import com.trendyol.cart.domain.product.Category;
import com.trendyol.cart.domain.product.Product;

import java.math.BigDecimal;
import java.util.Objects;

public class ShoppingCartItem {

  private Long id;
  private Product product;
  private int quantity;

  public ShoppingCartItem(Long id, Product product, int quantity) {
    this.id = id;
    this.product = product;
    this.quantity = quantity;
  }

  public ShoppingCartItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void incrementQuantityBy(int increment) {
    this.quantity = this.quantity + increment;
  }

  public Category getCategoryOfProduct() {
    return product.getCategory();
  }

  public BigDecimal getItemTotalAmount() {
    return product.getPrice().multiply(new BigDecimal(quantity));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ShoppingCartItem that = (ShoppingCartItem) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "ShoppingCartItem{" +
        "id=" + id +
        ", product=" + product +
        ", quantity=" + quantity +
        '}';
  }
}
