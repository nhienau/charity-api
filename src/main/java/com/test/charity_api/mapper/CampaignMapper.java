package com.test.charity_api.mapper;

import com.test.charity_api.dto.CampaignDTO;
import com.test.charity_api.entity.Account;
import com.test.charity_api.entity.Campaign;
import com.test.charity_api.util.DateTimeUtil;
import java.text.ParseException;
import java.util.Date;

public class CampaignMapper {

    public static Campaign mapToCampaign(CampaignDTO c) throws ParseException {
        if (c == null) {
            return new Campaign();
        }
        Campaign entity = new Campaign();
        Account createdBy = new Account();
        createdBy.setId(c.getCreatedBy());
        entity.setId(c.getId());
        entity.setName(c.getName());
        entity.setDescription(c.getDescription());
        entity.setCreatedAt(c.getCreatedAt());
        Date closeDate = DateTimeUtil.stringToDate(c.getCloseDateStr());
        entity.setCloseDate(closeDate);
        entity.setTargetAmount(c.getTargetAmount());
        entity.setCurrentAmount(c.getCurrentAmount());
        entity.setDonationCount(c.getDonationCount());
        entity.setPostId(c.getPostId());
        entity.setCreatedBy(createdBy);
        entity.setStatus(c.isStatus());
        return entity;
    }

    public static CampaignDTO mapToCampaignDto(Campaign c) {
        if (c == null) {
            return null;
        }
        CampaignDTO entity = new CampaignDTO();
        entity.setId(c.getId());
        entity.setName(c.getName());
        entity.setDescription(c.getDescription());
        entity.setCreatedAt(c.getCreatedAt());
        entity.setCloseDate(c.getCloseDate());
        entity.setTargetAmount(c.getTargetAmount());
        entity.setCurrentAmount(c.getCurrentAmount());
        entity.setDonationCount(c.getDonationCount());
        entity.setPostId(c.getPostId());
        entity.setStatus(c.isStatus());
        return entity;
    }
}
