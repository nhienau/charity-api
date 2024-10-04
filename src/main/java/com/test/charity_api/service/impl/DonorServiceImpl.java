package com.test.charity_api.service.impl;

import com.test.charity_api.dto.DonorDTO;
import com.test.charity_api.entity.Donor;
import com.test.charity_api.mapper.DonorMapper;
import com.test.charity_api.repository.DonorRepository;
import com.test.charity_api.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonorServiceImpl implements DonorService {

    @Autowired
    private DonorRepository donorRepository;

    @Override
    public DonorDTO findByPhoneNumber(String phoneNumber) {
        Donor d = donorRepository.findByStatusTrueAndPhoneNumber(phoneNumber);
        return DonorMapper.mapToDonorDto(d);
    }

    @Override
    public DonorDTO insertDonor(DonorDTO d) {
        Donor temp = DonorMapper.mapToDonor(d);
        Donor saved = donorRepository.save(temp);
        return DonorMapper.mapToDonorDto(saved);
    }

}
