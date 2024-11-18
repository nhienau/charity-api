package com.test.charity_api.controller;

import com.test.charity_api.dto.DonationResponse;
import com.test.charity_api.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DonationResponse> getCampaignDonation(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "campaignId", required = true) Long campaignId,
            @RequestParam(value = "name", defaultValue = "", required = false) String name) {
        return new ResponseEntity<>(donationService.getDonation(pageNo, pageSize, campaignId, name), HttpStatus.OK);
    }

    //phần HuyLe
    @GetMapping("/searchDonations")
    public ResponseEntity<DonationResponse> searchDonations(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "campaignName", required = true) String campaignName,
            @RequestParam(value = "donorName", defaultValue = "", required = false) String donorName,
            @RequestParam(value = "startDate", required = true) String startDate,
            @RequestParam(value = "endDate", required = true) String endDate) {
        return new ResponseEntity<>(donationService.searchDonations(pageNo, pageSize, campaignName, donorName, startDate, endDate), HttpStatus.OK);
    }
}
