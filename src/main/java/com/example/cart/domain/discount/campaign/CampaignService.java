package com.example.cart.domain.discount.campaign;

import java.util.List;

public interface CampaignService {

  List<Campaign> retrieveCampaignsForCategories(List<Long> categoryIdList);
}
