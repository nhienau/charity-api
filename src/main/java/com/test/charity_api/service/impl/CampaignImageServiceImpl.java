package com.test.charity_api.service.impl;

import com.test.charity_api.dto.CampaignImageDTO;
import com.test.charity_api.entity.Campaign;
import com.test.charity_api.entity.CampaignImage;
import com.test.charity_api.mapper.CampaignImageMapper;
import com.test.charity_api.repository.CampaignImageRepository;
import com.test.charity_api.repository.CampaignRepository;
import com.test.charity_api.service.CampaignImageService;
import com.test.charity_api.service.CloudinaryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CampaignImageServiceImpl implements CampaignImageService {

    @Autowired
    private CampaignImageRepository campaignImageRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public CampaignImageDTO add(String url, int campaignId) {
        Campaign c = campaignRepository.findByStatusTrueAndId(campaignId);
        if (c == null) {
            throw new RuntimeException("No campaign found");
        }
        CampaignImage record = new CampaignImage();
        record.setUrl(url);
        record.setCampaign(c);
        CampaignImage saved = campaignImageRepository.save(record);
        return CampaignImageMapper.mapToCampaignImageDto(saved);
    }

    @Override
    public void delete(List<Integer> ids) {
        List<CampaignImage> images = campaignImageRepository.findByIdsIn(ids);
        for (CampaignImage img : images) {
            campaignImageRepository.deleteById(img.getId());

            // Get image's public ID
            String url = img.getUrl();
            String[] parts = url.split("/");
            String lastPart = parts[parts.length - 1];
            String[] fileParts = lastPart.split("\\.");
            String publicId = fileParts[0];
            cloudinaryService.delete(publicId);
        }
    }
}
