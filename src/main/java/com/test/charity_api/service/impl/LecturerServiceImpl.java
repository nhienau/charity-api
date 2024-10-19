/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.test.charity_api.service.impl;

import com.test.charity_api.dto.LecturerDTO;
import com.test.charity_api.dto.LecturerResponse;
import com.test.charity_api.entity.Lecturer;
import com.test.charity_api.mapper.LecturerMapper;
import com.test.charity_api.repository.LecturerRepository;
import com.test.charity_api.service.LecturerService;
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
public class LecturerServiceImpl implements LecturerService{

    @Autowired
    private LecturerRepository lecturerRepository;
    
    @Override
    public List<LecturerDTO> getAll() {
        List<Lecturer> lecturers = (ArrayList) lecturerRepository.findAll();
        return lecturers.stream().map(l -> LecturerMapper.mapToLecturerDto(l)).collect(Collectors.toList());
    }

    @Override
    public LecturerDTO findById(int id) {
        Lecturer l = lecturerRepository.findByStatusTrueAndId(id);
        if (l != null && !l.isStatus()) {
            return null;
        }
        return LecturerMapper.mapToLecturerDto(l);
    }

    @Override
    public void createLecturer(LecturerDTO req) {
        LecturerDTO temp = new LecturerDTO();
        temp.setId(req.getId());
        temp.setName(req.getName());
        temp.setCampaign(req.getCampaign());
        Lecturer l = new Lecturer();
        try {
            l = LecturerMapper.mapToLecturer(temp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        lecturerRepository.save(l);    
    }

    @Override
    public void updateLecturer(LecturerDTO req) {
        Lecturer l = LecturerMapper.mapToLecturer(req);
        lecturerRepository.save(l);
    }

    @Override
    public void deleteLecturer(int id) {
        lecturerRepository.deleteLecturer(id);
    }

    @Override
    public LecturerResponse getLecturers(int pageNo, int pageSize, String query) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Lecturer> result = lecturerRepository.findByStatusTrueAndNameContaining(query, pageable);
        List<LecturerDTO> content = result.getContent().stream()
                .map(l -> LecturerMapper.mapToLecturerDto(l))
                .collect(Collectors.toList());

        LecturerResponse response = new LecturerResponse();
        response.setContent(content);
        response.setPageNo(pageNo + 1);
        response.setPageSize(pageSize);
        response.setTotalElements(result.getTotalElements());
        response.setTotalPages(result.getTotalPages());
        response.setLast(result.isLast());
        return response;
    }

    @Override
    public boolean lecturerDoesntHaveAnyCampaign(int id) {
        return !lecturerRepository.lecturerIsHavingAnActiveCampain(id);
    }
    
}
