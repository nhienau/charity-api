package com.test.charity_api.controller;

import com.test.charity_api.dto.DonorNameResponse;
import com.test.charity_api.service.DonorNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donorName")
public class DonorNameController {

    @Autowired
    private DonorNameService donorNameService;

    @GetMapping("/get")
    public ResponseEntity<DonorNameResponse> getDonorNamesByPhoneNumber(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "phoneNumber", defaultValue = "", required = true) String phoneNumber
    ) {
        return new ResponseEntity<>(donorNameService.getDonorNamesByPhoneNumber(pageNo, pageSize, phoneNumber), HttpStatus.OK);
    }
}
