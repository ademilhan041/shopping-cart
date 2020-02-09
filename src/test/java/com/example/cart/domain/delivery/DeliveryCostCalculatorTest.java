package com.example.cart.domain.delivery;

import com.example.cart.domain.Data;
import com.example.cart.domain.ShoppingCart;
import com.example.cart.domain.ShoppingCartItem;
import com.example.cart.domain.product.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DeliveryCostCalculatorTest {

  private DeliveryCostCalculator calculator;

  @Before
  public void setUp() {
    BigDecimal costPerDelivery = new BigDecimal("10");
    BigDecimal costPerProduct = new BigDecimal("5");
    BigDecimal fixedCost = new BigDecimal("2.99");

    calculator = new DeliveryCostCalculator(costPerDelivery, costPerProduct, fixedCost);
  }

  @Test
  public void should_calculate_total_per_delivery_by_multiplying_initial_per_delivery_cost_with_distinct_category_count() {
    // given
    Product product1 = Data.product(1L, BigDecimal.ONE, Data.category(1L));
    Product product2 = Data.product(2L, BigDecimal.ONE, Data.category(2L));

    ShoppingCart cart = ShoppingCart.createCart(
        ShoppingCartItem.create(product1, 2),
        ShoppingCartItem.create(product2, 10)
    );

    // when
    BigDecimal totalPerDelivery = calculator.calculateTotalPerDelivery(cart);

    // then
    assertThat(totalPerDelivery).isEqualTo(new BigDecimal("20"));
  }

  @Test
  public void should_calculate_total_per_products_by_multiplying_initial_per_product_cost_with_distinct_product_count() {
    // given
    Product product1 = Data.product(1L, BigDecimal.ONE, Data.category(1L));
    Product product2 = Data.product(2L, BigDecimal.ONE, Data.category(2L));

    ShoppingCart cart = ShoppingCart.createCart(
        ShoppingCartItem.create(product1, 2),
        ShoppingCartItem.create(product2, 10)
    );

    // when
    BigDecimal totalPerDelivery = calculator.calculateTotalPerProducts(cart);

    // then
    assertThat(totalPerDelivery).isEqualTo(new BigDecimal("10"));
  }

  @Test
  public void should_calculate_delivery_cost_by_summing_up_total_per_delivery_and_total_per_product_cost_and_fixed_cost() {
    // given
    Product product1 = Data.product(1L, BigDecimal.ONE, Data.category(1L));
    Product product2 = Data.product(2L, BigDecimal.ONE, Data.category(2L));

    ShoppingCart cart = ShoppingCart.createCart(
        ShoppingCartItem.create(product1, 2),
        ShoppingCartItem.create(product2, 10)
    );

    // when
    BigDecimal totalPerDelivery = calculator.calculateDeliveryCost(cart);

    // then
    assertThat(totalPerDelivery).isEqualTo(new BigDecimal("32.99"));
  }
}
