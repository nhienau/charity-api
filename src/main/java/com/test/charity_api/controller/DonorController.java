package com.test.charity_api.controller;

import com.test.charity_api.dto.DonorDTO;
import com.test.charity_api.dto.DonorResponse;
import com.test.charity_api.entity.Donor;
import com.test.charity_api.mapper.DonorMapper;
import com.test.charity_api.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/donor")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @GetMapping("/getAll")
    public ResponseEntity<DonorResponse> getDonors(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "12", required = false) int pageSize,
            @RequestParam(value = "query", defaultValue = "", required = false) String query,
            @RequestParam(value = "filter", defaultValue = "student", required = true) String filter
    ) {
        // filter: all/students/strangers
        return new ResponseEntity<>(donorService.getDonors(pageNo, pageSize, query, filter), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<DonorDTO> getDonor(@RequestParam String id) {
        DonorDTO l = donorService.findById(id);
        return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @PostMapping("/create")
    public DonorDTO createDonor(@RequestBody DonorDTO donorDTO) {
        if (donorService.existsById(donorDTO.getId())) {
            return null;
        }
        DonorDTO result = donorService.insertDonor(donorDTO);
        return result;
    }

    @PutMapping("/update")
    public String updateDonor(@RequestBody DonorDTO donorDTO) {
        if (donorService.findById(donorDTO.getId()) == null) {
            return "fail";
        }
        donorService.updateDonor(donorDTO);
        return "success";
    }

    @DeleteMapping("/delete")
    public DonorDTO deleteDonor(@RequestParam String id) {
        if (donorService.findById(id) == null) {
            return null;
        }
        Donor donor = donorService.deleteDonor(id);
        return DonorMapper.mapToDonorDto(donor);
    }
}
