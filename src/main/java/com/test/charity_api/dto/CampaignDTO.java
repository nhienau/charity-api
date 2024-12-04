package com.test.charity_api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class CampaignDTO {

    private int id;
    private String name;
    private String description;
    private Date createdAt = new Date();
    private Date closeDate;
    private String closeDateStr;
    private long targetAmount;
    private long currentAmount = 0;
    private int donationCount = 0;
    private Integer postId;
    private Integer disbursementPostId;
    private Integer postDonationPostId;
    private int createdBy;
    private LecturerDTO lecturer;
    private boolean status = true;
    private List<CampaignImageDTO> campaignImage = new ArrayList<>();

    public CampaignDTO() {
    }

    public CampaignDTO(int id, String name, String description, Date createdAt, Date closeDate, String closeDateStr, long targetAmount, long currentAmount, int donationCount, Integer postId, int createdBy, LecturerDTO lecturer, List<CampaignImageDTO> campaignImage, boolean status) {
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
        this.lecturer = lecturer;
        this.status = status;
        this.campaignImage = campaignImage;
    }

}
