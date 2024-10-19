package com.test.charity_api.service;

import com.test.charity_api.dto.CampaignImageDTO;
import java.util.List;

public interface CampaignImageService {

    public CampaignImageDTO add(String url, int campaignId);

    public void delete(List<Integer> ids);
}
