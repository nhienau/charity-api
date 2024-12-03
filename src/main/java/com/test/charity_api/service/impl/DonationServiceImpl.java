package com.test.charity_api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.charity_api.dto.DonationDTO;
import com.test.charity_api.dto.DonationResponse;
import com.test.charity_api.entity.Donation;
import com.test.charity_api.mapper.DonationMapper;
import com.test.charity_api.repository.DonationRepository;
import com.test.charity_api.service.DonationService;
import com.test.charity_api.util.DateTimeUtil;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Override
    public DonationResponse getDonation(int pageNo, int pageSize, Long campaignId, String name) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Donation> result;

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

    // phần huyle
    @Override
    public DonationResponse searchDonations(int pageNo, int pageSize, String campaignName, String donorName, String startDate, String endDate) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Date start = null;
        Date end = null;
        try {
            if (!startDate.isEmpty()) {
                start = DateTimeUtil.stringToDate(startDate);
            }
            if (!endDate.isEmpty()) {
                end = DateTimeUtil.stringToDate(endDate);
            }
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        if (start != null && end != null && start.after(end)) {
            throw new RuntimeException("Ngày bắt đầu không thể lớn hơn ngày kết thúc.");
        }
        
        Page<Donation> result;

        String donorNameQueryStr = donorName.trim().toLowerCase();
//        if (donorNameQueryStr.isEmpty()) {
//            result = donationRepository.findByCampaignIdOrderByCreatedAtDesc(campaignId, pageable);
//        } else if ("nhà hảo tâm".contains(queryStr) || "nha hao tam".contains(queryStr)) {
//            result = donationRepository.findByCampaignIdAndDonorNameIncludeAnonymousName(campaignId, queryStr, pageable);
//        } else {
//            result = donationRepository.findByCampaignIdAndDonorName(campaignId, queryStr, pageable);
//        }
        
        boolean includeAnonymousDonor = "nhà hảo tâm".contains(donorNameQueryStr) || "nha hao tam".contains(donorNameQueryStr);

        result = donationRepository.findByDonorNameAndCampaignNameAndDateRange(donorName, campaignName, start, end, includeAnonymousDonor, pageable);
        List<DonationDTO> content = result.getContent().stream()
                .map(d -> DonationMapper.mapToDonationDto(d, true, true, true, false))
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
    public DonationResponse getDonationHistory(String username, int pageNo, int pageSize, String campaignName, String fromDate, String toDate) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Date from = null;
        Date to = null;
        try {
            if (!fromDate.isEmpty()) {
                from = DateTimeUtil.stringToDate(fromDate);
            }
            if (!toDate.isEmpty()) {
                to = DateTimeUtil.stringToDate(toDate);
            }
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        Page<Donation> result = donationRepository.findByDonorIdAndCampaignNameAndDateRange(username, campaignName, from, to, pageable);
        List<DonationDTO> content = result.getContent().stream()
                .map(d -> DonationMapper.mapToDonationDto(d, true, false, false, false))
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

}
