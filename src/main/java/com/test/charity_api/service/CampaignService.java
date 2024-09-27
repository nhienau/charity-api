package com.test.charity_api.service;

import com.test.charity_api.dto.CampaignDTO;
import com.test.charity_api.dto.CampaignResponse;
import java.util.List;

public interface CampaignService {

    public List<CampaignDTO> getAll();

    public CampaignDTO findById(int id);

    public void createCampaign(CampaignDTO c);

    public void deleteCampaign(int id);

    public CampaignResponse getCampaigns(int pageNo, int pageSize, String query);
}
