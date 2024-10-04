package com.test.charity_api.service.impl;

import com.test.charity_api.dto.DonationDTO;
import com.test.charity_api.entity.Donation;
import com.test.charity_api.mapper.DonationMapper;
import com.test.charity_api.repository.DonationRepository;
import com.test.charity_api.service.DonationService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Override
    public List<DonationDTO> getDonation(Long campaignId) {
        List<Donation> list = donationRepository.getDonation(campaignId);
        return list.stream().map(d -> DonationMapper.mapToDonationDto(d)).collect(Collectors.toList());
    }

    @Override
    public void insert(DonationDTO d) {
        donationRepository.insert(d.getCampaign().getId(), d.getDonor().getId(), d.getAmount(), d.getCreatedAt(), d.getTransactionId(),
                d.getDonorNameId());
    }

}
