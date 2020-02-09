package com.example.cart.domain.product;

import java.util.Objects;

public class Category {

  private Long id;
  private String title;
  private Category parent;

  public static Category create(Long id, String title) {
    Category category = new Category();
    category.setId(id);
    category.setTitle(title);
    return category;
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

  public Category getParent() {
    return parent;
  }

  public void setParent(Category parent) {
    this.parent = parent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Category category = (Category) o;
    return Objects.equals(id, category.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Category{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", parent=" + parent +
        '}';
  }
}
