package com.test.charity_api.controller;

import com.test.charity_api.dto.DonationResponse;
import com.test.charity_api.security.JWTGenerator;
import com.test.charity_api.service.DonationService;
import com.test.charity_api.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;
    private JWTGenerator jwtGenerator;

    @Autowired
    public DonationController(DonationService donationService, JWTGenerator jwtGenerator) {
        this.donationService = donationService;
        this.jwtGenerator = jwtGenerator;
    }

    @GetMapping("/getCampaignDonation")
    public ResponseEntity<DonationResponse> getCampaignDonation(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "campaignId", required = true) Long campaignId,
            @RequestParam(value = "name", defaultValue = "", required = false) String name) {
        return new ResponseEntity<>(donationService.getDonation(pageNo, pageSize, campaignId, name), HttpStatus.OK);
    }

    //phần HuyLe
    @GetMapping("/search")
    public ResponseEntity<DonationResponse> searchDonations(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "donorName", defaultValue = "", required = false) String donorName,
            @RequestParam(value = "fromDate", required = true) String fromDate,
            @RequestParam(value = "toDate", required = true) String toDate) {
        if (!fromDate.isEmpty() && !toDate.isEmpty() && (!DateTimeUtil.isISOString(fromDate) || !DateTimeUtil.isISOString(toDate))) {
            throw new RuntimeException("Invalid fromDate or toDate");
        }

        return new ResponseEntity<>(donationService.searchDonations(pageNo, pageSize, phoneNumber, campaignName, donorName, fromDate, toDate), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<DonationResponse> getDonationHistory(
            @CookieValue("accessToken") String token,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate) {
        String username = jwtGenerator.getUsernameFromJWT(token);
        if (username == null) {
            throw new RuntimeException("User not found");
        }

        if (!fromDate.isEmpty() && !toDate.isEmpty() && (!DateTimeUtil.isISOString(fromDate) || !DateTimeUtil.isISOString(toDate))) {
            throw new RuntimeException("Invalid fromDate or toDate");
        }

        return new ResponseEntity<>(donationService.getDonationHistory(username, pageNo, pageSize, campaignName, fromDate, toDate), HttpStatus.OK);
    }
}
