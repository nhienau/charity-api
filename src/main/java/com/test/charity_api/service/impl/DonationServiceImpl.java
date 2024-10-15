package com.test.charity_api.service.impl;

import com.test.charity_api.dto.DonationDTO;
import com.test.charity_api.dto.DonationResponse;
import com.test.charity_api.entity.Donation;
import com.test.charity_api.mapper.DonationMapper;
import com.test.charity_api.repository.DonationRepository;
import com.test.charity_api.service.DonationService;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Override
    public DonationResponse getDonation(int pageNo, int pageSize, Long campaignId, String name) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Donation> result = null;

        String queryStr = name.trim().toLowerCase();
        if (queryStr.isEmpty()) {
            result = donationRepository.findByCampaignIdOrderByCreatedAtDesc(campaignId, pageable);
        } else if ("nhà hảo tâm".contains(queryStr) || "nha hao tam".contains(queryStr)) {
            result = donationRepository.findByCampaignIdAndDonorNameIncludeAnonymousName(campaignId, queryStr, pageable);
        } else {
            result = donationRepository.findByCampaignIdAndDonorName(campaignId, queryStr, pageable);
        }

        List<DonationDTO> content = result.getContent().stream()
                .map(d -> DonationMapper.mapToDonationDto(d, false, true, true, false))
                .collect(Collectors.toList());

        DonationResponse response = new DonationResponse();
        response.setContent(content);
        response.setPageNo(pageNo + 1);
        response.setPageSize(pageSize);
        response.setTotalElements(result.getTotalElements());
        response.setTotalPages(result.getTotalPages());
        response.setLast(result.isLast());
        return response;
    }

    @Override
    public void insert(DonationDTO d) throws ParseException {
        Donation entity = null;
        try {
            entity = DonationMapper.mapToDonation(d);
        } catch (ParseException ex) {
            throw ex;
        }
        donationRepository.save(entity);
    }

}
