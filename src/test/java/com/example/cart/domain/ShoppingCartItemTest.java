package com.example.cart.domain;

import com.example.cart.domain.product.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartItemTest {

  @Test
  public void should_calculate_total_amount_for_item_by_multiplying_quantity_and_product_price() {
    // given
    Product product = Product.create(1L, "P1", new BigDecimal("5.99"), null);
    ShoppingCartItem item = ShoppingCartItem.create(product, 2);

    // when
    BigDecimal itemTotalAmount = item.getItemTotalAmount();

    // then
    assertThat(itemTotalAmount).isEqualTo(new BigDecimal("11.98"));
  }
}
