package com.trendyol.cart.infrastructure.client;

import com.trendyol.cart.domain.discount.campaign.Campaign;
import com.trendyol.cart.domain.discount.campaign.CampaignService;

import java.util.ArrayList;
import java.util.List;

public class CampaignMockService implements CampaignService {

  @Override
  public List<Campaign> retrieveCampaignsForCategories(List<Long> categoryIdList) {
    return new ArrayList<>();
  }
}
