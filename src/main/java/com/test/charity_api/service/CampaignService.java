package com.test.charity_api.service;

import com.test.charity_api.dto.CampaignDTO;
import com.test.charity_api.dto.CampaignResponse;
import java.util.List;

public interface CampaignService {

    public List<CampaignDTO> getAll();

    public CampaignDTO findById(int id);

    public CampaignDTO createCampaign(CampaignDTO c);

    public CampaignDTO deleteCampaign(int id);

    public CampaignResponse getCampaigns(int pageNo, int pageSize, String query, String filter);

    public void updateDonation(int id, long amount);

    public CampaignDTO updateCampaign(CampaignDTO c);
}
