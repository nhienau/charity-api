package com.test.charity_api.dto;

import java.util.Date;
import lombok.Data;

@Data
public class DonationDTO {

    private int id;
    private CampaignDTO campaign;
    private DonorDTO donor;
    private long amount;
    private Date createdAt;
    private String transactionId;
    private DonorNameDTO donorName;

    public DonationDTO() {
    }

    public DonationDTO(int id, CampaignDTO campaign, DonorDTO donor, long amount, Date createdAt, String transactionId, DonorNameDTO donorName) {
        this.id = id;
        this.campaign = campaign;
        this.donor = donor;
        this.amount = amount;
        this.createdAt = createdAt;
        this.transactionId = transactionId;
        this.donorName = donorName;
    }
}
