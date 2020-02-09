package com.example.cart.domain;

import com.example.cart.domain.discount.Discount;
import com.example.cart.domain.discount.DiscountType;
import com.example.cart.domain.discount.campaign.Campaign;
import com.example.cart.domain.discount.coupon.Coupon;
import com.example.cart.domain.product.Category;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class ShoppingCartTest {

  @Test
  public void should_create_empty_cart() {
    // when
    ShoppingCart cart = ShoppingCart.createEmptyCart();

    // then
    assertThat(cart.getCartItems().isEmpty()).isTrue();
    assertThat(cart.getCouponId()).isNull();
  }

  @Test
  public void should_create_with_coupon() {
    // when
    ShoppingCart cart = ShoppingCart.createWithCoupon(1L);

    // then
    assertThat(cart.getCartItems().isEmpty()).isTrue();
    assertThat(cart.getCouponId()).isEqualTo(1L);
  }

  @Test
  public void should_create_with_items() {
    // when
    ShoppingCart cart = ShoppingCart.createCart(
        ShoppingCartItem.create(Data.product(), 2),
        ShoppingCartItem.create(Data.product(), 10)
    );

    // then
    assertThat(cart.getCartItems().size()).isEqualTo(2);
    assertThat(cart.getCouponId()).isNull();
  }

  @Test
  public void should_create_new_item_when_item_to_be_added_is_not_in_cart() {
    // given
    ShoppingCart cart = ShoppingCart.createEmptyCart();

    // when
    cart.addItem(Data.product(), 1);

    // then
    assertThat(cart.getCartItems().size()).isEqualTo(1);
    assertThat(cart.getCartItems().get(0).getProduct().getId()).isEqualTo(1L);
    assertThat(cart.getCartItems().get(0).getQuantity()).isEqualTo(1);
  }

  @Test
  public void should_update_existed_item_when_item_to_be_added_is_in_cart() {
    // given
    ShoppingCart cart = ShoppingCart.createEmptyCart();

    // when
    cart.addItem(Data.product(), 1);
    cart.addItem(Data.product(), 2);

    // then
    assertThat(cart.getCartItems().size()).isEqualTo(1);
    assertThat(cart.getCartItems().get(0).getProduct().getId()).isEqualTo(1L);
    assertThat(cart.getCartItems().get(0).getQuantity()).isEqualTo(3);
  }

  @Test
  public void should_find_item_by_given_product() {
    // given
    ShoppingCart cart = ShoppingCart.createEmptyCart();
    cart.addItem(Data.product(), 1);

    // when
    Optional<ShoppingCartItem> foundItem = cart.findItemByProduct(Data.product());

    // then
    assertThat(foundItem.isPresent()).isTrue();
    assertThat(foundItem.get().getProduct().getId()).isEqualTo(1L);
  }

  @Test
  public void should_calculate_total_amount_by_summing_up_total_amount_of_cart_items() {
    // given
    ShoppingCart cart = ShoppingCart.createEmptyCart();
    cart.addItem(Data.product(1L, new BigDecimal(10)), 1);
    cart.addItem(Data.product(2L, new BigDecimal(20)), 2);

    // when
    BigDecimal cartTotalAmount = cart.calculateTotalAmount();

    // then
    assertThat(cartTotalAmount).isEqualTo(new BigDecimal(50));
  }

  @Test
  public void should_calculate_total_amount_for_given_category_by_summing_up_total_amount_of_cart_items_belong_to_given_category() {
    // given
    ShoppingCart cart = ShoppingCart.createEmptyCart();
    cart.addItem(Data.product(1L, new BigDecimal(10), Data.category(1L)), 1);
    cart.addItem(Data.product(2L, new BigDecimal(20), Data.category(2L)), 2);

    // when
    BigDecimal cartTotalAmountForCategory1 = cart.calculateTotalAmountForCategory(1L);
    BigDecimal cartTotalAmountForCategory2 = cart.calculateTotalAmountForCategory(2L);

    // then
    assertThat(cartTotalAmountForCategory1).isEqualTo(new BigDecimal(10));
    assertThat(cartTotalAmountForCategory2).isEqualTo(new BigDecimal(40));
  }

  @Test
  public void should_return_distinct_categories() {
    // given
    ShoppingCart cart = ShoppingCart.createEmptyCart();
    cart.addItem(Data.product(1L, new BigDecimal(10), Data.category(1L)), 1);
    cart.addItem(Data.product(2L, new BigDecimal(20), Data.category(2L)), 2);

    // when
    List<Category> categories = cart.distinctCategories();

    // then
    assertThat(categories.size()).isEqualTo(2);
  }

  @Test
  public void should_filter_applicable_campaigns_when_campaign_discount_is_applying() {
    // given
    ShoppingCart cart = spy(ShoppingCart.createEmptyCart());
    cart.addItem(Data.product(1L, new BigDecimal(10), Data.category(1L)), 1);
    cart.addItem(Data.product(2L, new BigDecimal(20), Data.category(2L)), 2);

    Campaign campaign1 = Campaign.create(1L, 1L, 30, Discount.create(DiscountType.Rate, new BigDecimal(25)));
    Campaign campaign2 = Campaign.create(2L, 2L, 30, Discount.create(DiscountType.Rate, new BigDecimal(50)));

    // when
    cart.applyCampaign(Arrays.asList(campaign1, campaign2));

    // then
    assertThat(cart.getCampaignDiscount()).isEqualTo(BigDecimal.ZERO);
  }

  @Test
  public void should_apply_max_discount_campaign_to_category_when_campaign_discount_is_applying() {
    // given
    Long categoryId = 2L;
    ShoppingCart cart = spy(ShoppingCart.createEmptyCart());
    cart.addItem(Data.product(1L, new BigDecimal(10), Data.category(categoryId)), 10);
    cart.addItem(Data.product(2L, new BigDecimal(20), Data.category(categoryId)), 10);

    Campaign campaign1 = Campaign.create(1L, categoryId, 5, Discount.create(DiscountType.Rate, new BigDecimal(25)));
    Campaign campaign2 = Campaign.create(2L, categoryId, 10, Discount.create(DiscountType.Amount, new BigDecimal(50)));

    // when
    cart.applyCampaign(Arrays.asList(campaign1, campaign2));

    // then
    assertThat(cart.getCampaignDiscount()).isEqualTo(new BigDecimal("75.00"));
  }

  @Test
  public void should_check_whether_coupon_is_applicable_or_not_when_coupon_discount_is_applying() {
    // given
    ShoppingCart cart = spy(ShoppingCart.createEmptyCart());
    cart.addItem(Data.product(1L, new BigDecimal(10), Data.category(1L)), 1);
    cart.addItem(Data.product(2L, new BigDecimal(20), Data.category(2L)), 2);

    Coupon coupon = spy(Coupon.create(1L, new BigDecimal(1000), Discount.create(DiscountType.Rate, new BigDecimal(25))));

    // when
    cart.applyCoupon(coupon);

    // then
    verify(coupon).isApplicable(any());
  }

  @Test
  public void should_campaign_discount_already_applied_when_coupon_discount_applied() {
    // given
    Long categoryId = 2L;
    ShoppingCart cart = spy(ShoppingCart.createEmptyCart());
    cart.addItem(Data.product(1L, new BigDecimal(10), Data.category(categoryId)), 10);
    cart.addItem(Data.product(2L, new BigDecimal(20), Data.category(categoryId)), 10);

    Discount discount = spy(Discount.create(DiscountType.Rate, new BigDecimal(25)));
    Coupon coupon = Coupon.create(1L, new BigDecimal(100), discount);

    Campaign campaign = Campaign.create(1L, categoryId, 5, Discount.create(DiscountType.Rate, new BigDecimal(25)));
    cart.applyCampaign(Arrays.asList(campaign));

    // when
    cart.applyCoupon(coupon);

    // then
    assertThat(cart.getCouponDiscount()).isEqualTo(new BigDecimal("56.25"));

    verify(coupon.getDiscount()).calculate(new BigDecimal("225.00"));
  }
}
