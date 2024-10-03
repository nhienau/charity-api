package com.test.charity_api.mapper;

import com.test.charity_api.dto.DonationDTO;
import com.test.charity_api.entity.Donation;

public class DonationMapper {

    public static DonationDTO mapToDonationDto(Donation d) {
        if (d == null) {
            return null;
        }
        DonationDTO dto = new DonationDTO();
        dto.setId(d.getId());
        dto.setCampaign(CampaignMapper.mapToCampaignDto(d.getCampaign()));
        dto.setDonor(DonorMapper.mapToDonorDto(d.getDonor()));
        dto.setAmount(d.getAmount());
        dto.setCreatedAt(d.getCreatedAt());
        dto.setTransactionId(d.getTransactionId());
        dto.setDonorNameId(d.getDonorNameId());
        return dto;
    }

}
