package com.example.cart.domain.discount.campaign;

import com.example.cart.domain.discount.Discount;
import com.example.cart.domain.discount.DiscountType;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CampaignTest {

  @Test
  public void should_applicable_if_given_product_count_is_greater_than_campaign_min_product_required() {
    // given
    int minProductRequired = 3;
    
    Campaign campaign = Campaign.create(1L, 2L, minProductRequired, sampleDiscount());

    // when
    boolean isApplicable = campaign.isApplicable(10);

    // then
    assertThat(isApplicable).isTrue();
  }

  @Test
  public void should_applicable_if_given_product_count_is_equal_to_campaign_min_product_required() {
    // given
    int minProductRequired = 3;

    Campaign campaign = Campaign.create(1L, 2L, minProductRequired, sampleDiscount());

    // when
    boolean isApplicable = campaign.isApplicable(3);

    // then
    assertThat(isApplicable).isTrue();
  }

  @Test
  public void should_not_applicable_if_given_product_count_is_less_than_campaign_min_product_required() {
    // given
    int minProductRequired = 3;

    Campaign campaign = Campaign.create(1L, 2L, minProductRequired, sampleDiscount());

    // when
    boolean isApplicable = campaign.isApplicable(1);

    // then
    assertThat(isApplicable).isFalse();
  }

  private Discount sampleDiscount() {
    return Discount.create(DiscountType.Rate, new BigDecimal("25"));
  }
}
