package com.test.charity_api.controller;

import com.test.charity_api.dto.CampaignDTO;
import com.test.charity_api.dto.CampaignResponse;
import com.test.charity_api.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping("/getAll")
    public ResponseEntity<CampaignResponse> getCampaigns(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "12", required = false) int pageSize,
            @RequestParam(value = "query", defaultValue = "", required = false) String query
    ) {
        return new ResponseEntity<>(campaignService.getCampaigns(pageNo, pageSize, query), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<CampaignDTO> getCampaign(@RequestParam int id) {
        CampaignDTO c = campaignService.findById(id);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @PostMapping("/create")
    public String createCampaign(@RequestBody CampaignDTO campaignDTO) {
        campaignService.createCampaign(campaignDTO);
        return "success";
    }

    @PutMapping("/delete")
    public String deleteCampaign(@RequestParam int id) {
        campaignService.deleteCampaign(id);
        return "success";
    }
}
