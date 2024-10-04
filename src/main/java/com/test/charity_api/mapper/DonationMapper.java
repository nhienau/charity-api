package com.test.charity_api.mapper;

import com.test.charity_api.dto.DonationDTO;
import com.test.charity_api.entity.Donation;
import java.text.ParseException;

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

    public static Donation mapToDonation(DonationDTO dto) throws ParseException {
        if (dto == null) {
            return null;
        }
        Donation d = new Donation();
        d.setId(dto.getId());
        d.setCampaign(CampaignMapper.mapToCampaign(dto.getCampaign()));
        d.setDonor(DonorMapper.mapToDonor(dto.getDonor()));
        d.setAmount(dto.getAmount());
        d.setCreatedAt(dto.getCreatedAt());
        d.setDonorNameId(dto.getDonorNameId());
        d.setTransactionId(dto.getTransactionId());
        return d;
    }

}
