/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.test.charity_api.controller;

import com.test.charity_api.dto.LecturerDTO;
import com.test.charity_api.dto.LecturerResponse;
import com.test.charity_api.service.LecturerService;
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
@RequestMapping("/lecturer")
public class LecturerController {
    @Autowired
    private LecturerService lecturerService;

    @GetMapping("/getAll")
    public ResponseEntity<LecturerResponse> getLecturers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "12", required = false) int pageSize,
            @RequestParam(value = "query", defaultValue = "", required = false) String query
    ) {
        return new ResponseEntity<>(lecturerService.getLecturers(pageNo, pageSize, query), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<LecturerDTO> getLecturer(@RequestParam int id) {
        LecturerDTO l = lecturerService.findById(id);
        return new ResponseEntity<>(l, HttpStatus.OK);
    }

    @PostMapping("/create")
    public String createLecturer(@RequestBody LecturerDTO lecturerDTO) {
        lecturerService.createLecturer(lecturerDTO);
        return "success";
    }
    
    @PostMapping
    public String updateLecturer(@RequestBody LecturerDTO lecturerDTO){
        lecturerService.updateLecturer(lecturerDTO);
        return "success";
    }

    @PutMapping("/delete")
    public String deleteLecturer(@RequestParam int id) {
        if(!lecturerService.lecturerDoesntHaveAnyCampaign(id)){
            return "fail";
        }
        lecturerService.deleteLecturer(id);
        return "success";
    }
}
