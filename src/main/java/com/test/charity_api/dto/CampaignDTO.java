package com.test.charity_api.dto;

import java.util.Date;
import lombok.Data;

@Data
public class CampaignDTO {

    private int id;
    private String name;
    private String description;
    private Date createdAt;
    private Date closeDate;
    private String closeDateStr;
    private long targetAmount;
    private long currentAmount;
    private int donationCount;
    private int postId;
    private int createdBy;
    private boolean status;

    public CampaignDTO() {
    }

    public CampaignDTO(int id, String name, String description, Date createdAt, Date closeDate, String closeDateStr, long targetAmount, long currentAmount, int donationCount, int postId, int createdBy, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.closeDate = closeDate;
        this.closeDateStr = closeDateStr;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.donationCount = donationCount;
        this.postId = postId;
        this.createdBy = createdBy;
        this.status = status;
    }

}
