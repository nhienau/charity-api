package com.test.charity_api.controller;

import com.test.charity_api.dto.CampaignDTO;
import com.test.charity_api.dto.CampaignImageDTO;
import com.test.charity_api.dto.CampaignResponse;
import com.test.charity_api.dto.ImageIdsRequest;
import com.test.charity_api.service.CampaignImageService;
import com.test.charity_api.service.CampaignService;
import com.test.charity_api.service.CloudinaryService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private CampaignImageService campaignImageService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/getAll")
    public ResponseEntity<CampaignResponse> getCampaigns(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "12", required = false) int pageSize,
            @RequestParam(value = "query", defaultValue = "", required = false) String query,
            @RequestParam(value = "filter", defaultValue = "all", required = true) String filter
    ) {
        // filter: all/opening/closed/fulfilled
        return new ResponseEntity<>(campaignService.getCampaigns(pageNo, pageSize, query, filter), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<CampaignDTO> getCampaign(@RequestParam int id) {
        CampaignDTO c = campaignService.findById(id);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody CampaignDTO campaignDTO) {
        CampaignDTO result = campaignService.createCampaign(campaignDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/delete")
    public ResponseEntity<CampaignDTO> deleteCampaign(@RequestParam int id) {
        CampaignDTO result = campaignService.deleteCampaign(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/uploadImages")
    public ResponseEntity<List<CampaignImageDTO>> uploadImages(@RequestParam("files") List<MultipartFile> files, @RequestParam("campaignId") int campaignId) throws IOException {
        List<CampaignImageDTO> results = new ArrayList<>();
        for (MultipartFile f : files) {
            String url = cloudinaryService.uploadFile(f);
            CampaignImageDTO result = campaignImageService.add(url, campaignId);
            results.add(result);
        }
        return new ResponseEntity<>(results, HttpStatus.CREATED);
    }

    @PostMapping("/deleteImages")
    public ResponseEntity<?> deleteImages(@RequestBody ImageIdsRequest req) {
        List<Integer> ids = req.getImageIds();
        campaignImageService.delete(ids);
        return new ResponseEntity<>(ids, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<CampaignDTO> updateCampaign(@RequestBody CampaignDTO campaign) {
        CampaignDTO result = campaignService.updateCampaign(campaign);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
