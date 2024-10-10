package com.test.charity_api.dto;

import lombok.Data;

@Data
public class CampaignImageDTO {

    private int id;
    private String url;
    private CampaignDTO campaign;

    public CampaignImageDTO() {
    }

    public CampaignImageDTO(int id, String url, CampaignDTO campaign) {
        this.id = id;
        this.url = url;
        this.campaign = campaign;
    }

}
