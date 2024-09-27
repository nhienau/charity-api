package com.test.charity_api.dto;

import java.util.Date;
import lombok.Data;

@Data
public class DonationDTO {
    
    private int id;
    private CampaignDTO campaign;
    private DonorDTO donor;
    private long amount;
    private boolean showIdentity;
    private Date createdAt;

    public DonationDTO() {
    }

    public DonationDTO(int id, CampaignDTO campaign, DonorDTO donor, long amount, boolean showIdentity, Date createdAt) {
        this.id = id;
        this.campaign = campaign;
        this.donor = donor;
        this.amount = amount;
        this.showIdentity = showIdentity;
        this.createdAt = createdAt;
    }
}
