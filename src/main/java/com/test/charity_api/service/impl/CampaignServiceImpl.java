package com.test.charity_api.service.impl;

import com.test.charity_api.dto.CampaignDTO;
import com.test.charity_api.dto.CampaignResponse;
import com.test.charity_api.entity.Campaign;
import com.test.charity_api.mapper.CampaignMapper;
import com.test.charity_api.repository.CampaignRepository;
import com.test.charity_api.service.CampaignService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    public void createCampaign(CampaignDTO campaignDTO) {
        Campaign c = new Campaign();
        try {
            c = CampaignMapper.mapToCampaign(campaignDTO);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        campaignRepository.createCampaign(c.getName(), c.getDescription(), c.getCloseDate(), c.getTargetAmount(), c.getCreatedBy().getId());
    }

    @Override
    public void deleteCampaign(int id) {
        campaignRepository.deleteCampaign(id);
    }

    @Override
    public CampaignDTO findById(int id) {
        Campaign c = campaignRepository.findByStatusTrueAndId(id);
        if (c != null && !c.isStatus()) {
            return null;
        }
        return CampaignMapper.mapToCampaignDto(c);
    }

    @Override
    public List<CampaignDTO> getAll() {
        List<Campaign> campaigns = (ArrayList) campaignRepository.findAll();
        return campaigns.stream().map(c -> CampaignMapper.mapToCampaignDto(c)).collect(Collectors.toList());
    }

    @Override
    public CampaignResponse getCampaigns(int pageNo, int pageSize, String query) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Campaign> result = campaignRepository.findByStatusTrueAndNameContainingOrderByCreatedAtDesc(query, pageable);
        List<CampaignDTO> content = result.getContent().stream()
                .map(c -> CampaignMapper.mapToCampaignDto(c))
                .collect(Collectors.toList());

        CampaignResponse response = new CampaignResponse();
        response.setContent(content);
        response.setPageNo(pageNo + 1);
        response.setPageSize(pageSize);
        response.setTotalElements(result.getTotalElements());
        response.setTotalPages(result.getTotalPages());
        response.setLast(result.isLast());
        return response;
    }

    @Override
    public void updateCurrentAmount(int id, long amount) {
        Campaign c = campaignRepository.findByStatusTrueAndId(id);
        c.setCurrentAmount(c.getCurrentAmount() + amount);
        campaignRepository.save(c);
    }
}
