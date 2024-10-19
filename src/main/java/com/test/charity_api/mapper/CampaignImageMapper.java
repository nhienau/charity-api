package com.test.charity_api.mapper;

import com.test.charity_api.dto.CampaignImageDTO;
import com.test.charity_api.entity.CampaignImage;

public class CampaignImageMapper {

    public static CampaignImageDTO mapToCampaignImageDto(CampaignImage ci) {
        if (ci == null) {
            return null;
        }

        CampaignImageDTO dto = new CampaignImageDTO();
        dto.setId(ci.getId());
        dto.setUrl(ci.getUrl());
        return dto;
    }

    public static CampaignImage mapToCampaignImage(CampaignImageDTO dto) {
        if (dto == null) {
            return null;
        }

        CampaignImage ci = new CampaignImage();
        ci.setId(dto.getId());
        ci.setUrl(dto.getUrl());
        return ci;
    }
}
