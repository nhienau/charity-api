package com.test.charity_api.controller;

import com.test.charity_api.dto.DonationDTO;
import com.test.charity_api.service.DonationService;
import com.test.charity_api.util.DonationIdentity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donation")
public class DonationController {
    
    @Autowired
    private DonationService donationService;
    
    @GetMapping("/getCampaignDonation")
    public List<DonationDTO> getCampaignDonation(@RequestParam Long campaignId) {
        List<DonationDTO> result = donationService.getDonation(campaignId);
        List<DonationDTO> list = DonationIdentity.toggleDonationIdentity(result);
        return list;
    }
}
