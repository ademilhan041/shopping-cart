package com.example.cart.domain.product;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

  private Long id;
  private String title;
  private BigDecimal price;
  private Category category;

  public static Product create(Long id, String title, BigDecimal price, Category category) {
    Product product = new Product();
    product.setId(id);
    product.setTitle(title);
    product.setPrice(price);
    product.setCategory(category);
    return product;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", price=" + price +
        ", category=" + category +
        '}';
  }
}
