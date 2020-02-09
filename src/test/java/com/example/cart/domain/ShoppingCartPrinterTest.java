package com.example.cart.domain;

import com.example.cart.domain.product.Category;
import com.example.cart.domain.product.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartPrinterTest {

  private ShoppingCartPrinter printer;

  @Before
  public void setUp() {
    printer = new ShoppingCartPrinter();
  }

  @Test
  public void should_print_cart_summary_and_calculation_result() {
    // given
    Category category = Category.create(1L, "T-Shirt");
    Product product = Product.create(1L, "T-1", new BigDecimal(10), category);

    ShoppingCart cart = ShoppingCart.createEmptyCart();
    cart.addItem(product, 1);

    ShoppingCartCalculation calculation = ShoppingCartCalculation.create(
        BigDecimal.valueOf(1),
        BigDecimal.valueOf(2),
        BigDecimal.valueOf(3),
        BigDecimal.valueOf(4),
        BigDecimal.valueOf(5));

    // when
    String content = printer.print(cart.getCartItems(), calculation);

    // then
    assertThat(content).isEqualTo("T-Shirt\t\tT-1\t\t1\t\t10\t\t10\n" +
        "\n" +
        "Total Amount before All Discounts: 1\n" +
        "Discount by Campaign: 3\n" +
        "Discount by Coupon: 4\n" +
        "Total Amount after All Discounts: 2\n" +
        "Delivery Cost: 5");
  }
}
