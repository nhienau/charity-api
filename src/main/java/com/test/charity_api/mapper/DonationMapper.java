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
        dto.setShowIdentity(d.isShowIdentity());
        dto.setDonorName(DonorNameMapper.mapToDonorNameDto(d.getDonorName()));
        return dto;
    }

    public static DonationDTO mapToDonationDto(Donation d, boolean mapCampaign, boolean mapDonor, boolean mapTransactionId) {
        if (d == null) {
            return null;
        }
        DonationDTO dto = new DonationDTO();
        dto.setId(d.getId());
        if (mapCampaign) {
            dto.setCampaign(CampaignMapper.mapToCampaignDto(d.getCampaign()));
        }
        if (mapDonor) {
            dto.setDonor(DonorMapper.mapToDonorDto(d.getDonor()));
        }
        dto.setAmount(d.getAmount());
        dto.setCreatedAt(d.getCreatedAt());
        if (mapTransactionId) {
            dto.setTransactionId(d.getTransactionId());
        }
        dto.setShowIdentity(d.isShowIdentity());
        dto.setDonorName(DonorNameMapper.mapToDonorNameDto(d.getDonorName()));
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
        d.setDonorName(DonorNameMapper.mapToDonorName(dto.getDonorName()));
        d.setTransactionId(dto.getTransactionId());
        d.setShowIdentity(dto.isShowIdentity());
        return d;
    }

}
