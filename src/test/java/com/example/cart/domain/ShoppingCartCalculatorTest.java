package com.example.cart.domain;

import com.example.cart.domain.delivery.DeliveryService;
import com.example.cart.domain.discount.Discount;
import com.example.cart.domain.discount.DiscountType;
import com.example.cart.domain.discount.campaign.Campaign;
import com.example.cart.domain.discount.campaign.CampaignService;
import com.example.cart.domain.discount.coupon.Coupon;
import com.example.cart.domain.discount.coupon.CouponService;
import com.example.cart.domain.discount.coupon.exception.CouponNotExist;
import com.example.cart.domain.product.Category;
import com.example.cart.domain.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartCalculatorTest {

  private ShoppingCartCalculator calculator;

  @Mock
  private CampaignService campaignService;

  @Mock
  private CouponService couponService;

  @Mock
  private DeliveryService deliveryService;

  @Mock
  private ShoppingCartPrinter shoppingCartPrinter;

  @Before
  public void setUp() {
    calculator = new ShoppingCartCalculator(campaignService, couponService, deliveryService, shoppingCartPrinter);
  }

  @Test
  public void should_calculate_totals_by_applying_discounts() {
    // given
    Category category = Category.create(1L, "T-Shirt");
    Product product = Product.create(1L, "T-1", new BigDecimal("150.00"), category);

    ShoppingCart cart = spy(ShoppingCart.createWithCoupon(100L));
    cart.addItem(product, 1);

    Campaign campaign = Campaign.create(1L, category.getId(), 1, Discount.create(DiscountType.Rate, new BigDecimal(25)));
    List<Campaign> campaignList = Arrays.asList(campaign);
    Coupon coupon = Coupon.create(1L, new BigDecimal(100), Discount.create(DiscountType.Amount, new BigDecimal("10.00")));

    when(campaignService.retrieveCampaignsForCategories(anyListOf(Long.class))).thenReturn(campaignList);
    when(couponService.retrieveCoupon(anyLong())).thenReturn(Optional.of(coupon));
    when(deliveryService.calculateDeliveryCost(any())).thenReturn(new BigDecimal("40.00"));

    // when
    ShoppingCartCalculation calculation = calculator.calculateTotal(cart);

    // then
    assertThat(calculation).isNotNull();
    assertThat(calculation.getTotalAmountBeforeDiscount()).isEqualTo(new BigDecimal("150.00"));
    assertThat(calculation.getTotalAmountAfterDiscount()).isEqualTo(new BigDecimal("102.50"));
    assertThat(calculation.getCampaignDiscount()).isEqualTo(new BigDecimal("37.50"));
    assertThat(calculation.getCouponDiscount()).isEqualTo(new BigDecimal("10.00"));
    assertThat(calculation.getDeliveryCost()).isEqualTo(new BigDecimal("40.00"));
  }

  @Test
  public void should_apply_campaign_discount_and_then_coupon_discount_when_calculating_total() {
    // given
    ShoppingCart cart = spy(ShoppingCart.createWithCoupon(100L));
    cart.addItem(Data.product(), 1);

    List<Campaign> campaignList = Arrays.asList(Data.campaign());
    Coupon coupon = Data.coupon();

    when(campaignService.retrieveCampaignsForCategories(anyListOf(Long.class))).thenReturn(campaignList);
    when(couponService.retrieveCoupon(anyLong())).thenReturn(Optional.of(coupon));

    // when
    ShoppingCartCalculation calculation = calculator.calculateTotal(cart);

    // then
    assertThat(calculation).isNotNull();

    InOrder inOrder = inOrder(cart);
    inOrder.verify(cart).applyCampaign(campaignList);
    inOrder.verify(cart).applyCoupon(coupon);
  }

  @Test
  public void should_throw_coupon_not_exist_exception_when_given_coupon_not_exist() {
    // given
    ShoppingCart cart = spy(ShoppingCart.createWithCoupon(100L));
    cart.addItem(Data.product(), 1);

    when(campaignService.retrieveCampaignsForCategories(anyListOf(Long.class))).thenReturn(new ArrayList<>());
    when(couponService.retrieveCoupon(anyLong())).thenReturn(Optional.empty());

    // when
    Throwable throwable = catchThrowable(() -> calculator.calculateTotal(cart));

    // then
    assertThat(throwable).isNotNull()
        .isInstanceOf(CouponNotExist.class);
  }

  @Test
  public void should_calculate_delivery_cost_at_the_end_when_calculating_total() {
    // given
    ShoppingCart cart = spy(ShoppingCart.createWithCoupon(100L));
    cart.addItem(Data.product(), 1);

    List<Campaign> campaignList = Arrays.asList(Data.campaign());
    Coupon coupon = Data.coupon();

    when(campaignService.retrieveCampaignsForCategories(anyListOf(Long.class))).thenReturn(campaignList);
    when(couponService.retrieveCoupon(anyLong())).thenReturn(Optional.of(coupon));

    // when
    ShoppingCartCalculation calculation = calculator.calculateTotal(cart);

    // then
    assertThat(calculation).isNotNull();

    InOrder inOrder = inOrder(cart, deliveryService);
    inOrder.verify(cart).applyCampaign(campaignList);
    inOrder.verify(cart).applyCoupon(coupon);
    inOrder.verify(deliveryService).calculateDeliveryCost(cart);
  }


  @Test
  public void should_print_cart_and_calculation_info_at_last() {
    // given
    ShoppingCart cart = spy(ShoppingCart.createWithCoupon(100L));
    cart.addItem(Data.product(), 1);

    List<Campaign> campaignList = Arrays.asList(Data.campaign());
    Coupon coupon = Data.coupon();

    when(campaignService.retrieveCampaignsForCategories(anyListOf(Long.class))).thenReturn(campaignList);
    when(couponService.retrieveCoupon(anyLong())).thenReturn(Optional.of(coupon));

    // when
    ShoppingCartCalculation calculation = calculator.calculateTotal(cart);

    // then
    assertThat(calculation).isNotNull();

    InOrder inOrder = inOrder(cart, deliveryService, shoppingCartPrinter);
    inOrder.verify(cart).applyCampaign(campaignList);
    inOrder.verify(cart).applyCoupon(coupon);
    inOrder.verify(deliveryService).calculateDeliveryCost(cart);
    inOrder.verify(shoppingCartPrinter).print(cart.getCartItems(), calculation);
  }

}
