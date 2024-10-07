package com.test.charity_api.service.impl;

import com.test.charity_api.dto.DonorNameDTO;
import com.test.charity_api.entity.DonorName;
import com.test.charity_api.mapper.DonorNameMapper;
import com.test.charity_api.repository.DonorNameRepository;
import com.test.charity_api.service.DonorNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonorNameServiceImpl implements DonorNameService {

    @Autowired
    private DonorNameRepository donorNameRepository;

    @Override
    public DonorNameDTO findByNameAndDonorId(String name, int donorId) {
        DonorName dn = donorNameRepository.findByNameAndDonorId(name, donorId);
        return DonorNameMapper.mapToDonorNameDto(dn);
    }

    @Override
    public DonorNameDTO insert(DonorNameDTO dn) {
        DonorName temp = DonorNameMapper.mapToDonorName(dn);
        DonorName saved = donorNameRepository.save(temp);
        return DonorNameMapper.mapToDonorNameDto(saved);
    }

}
