package com.example.cart.infrastructure.client;

import com.example.cart.domain.discount.campaign.Campaign;
import com.example.cart.domain.discount.campaign.CampaignService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CampaignFakeService implements CampaignService {

  private List<Campaign> campaigns = new ArrayList<>();

  // For test data
  public void createCampaign(Campaign campaign) {
    campaigns.add(campaign);
  }

  @Override
  public List<Campaign> retrieveCampaignsForCategories(List<Long> categoryIdList) {
    return campaigns.stream()
        .filter(c -> categoryIdList.contains(c.getCategoryId()))
        .collect(Collectors.toList());
  }
}
