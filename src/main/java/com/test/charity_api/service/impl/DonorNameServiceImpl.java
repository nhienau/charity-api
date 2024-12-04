package com.test.charity_api.service.impl;

import com.test.charity_api.dto.DonorNameDTO;
import com.test.charity_api.dto.DonorNameResponse;
import com.test.charity_api.entity.DonorName;
import com.test.charity_api.mapper.DonorNameMapper;
import com.test.charity_api.repository.DonorNameRepository;
import com.test.charity_api.service.DonorNameService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DonorNameServiceImpl implements DonorNameService {

    @Autowired
    private DonorNameRepository donorNameRepository;

    @Override
    public DonorNameDTO findByNameAndDonorId(String name, String donorId) {
        DonorName dn = donorNameRepository.findByNameAndDonorId(name, donorId);
        return DonorNameMapper.mapToDonorNameDto(dn);
    }

    @Override
    public DonorNameDTO insert(DonorNameDTO dn) {
        DonorName temp = DonorNameMapper.mapToDonorName(dn);
        DonorName saved = donorNameRepository.save(temp);
        return DonorNameMapper.mapToDonorNameDto(saved);
    }

    @Override
    public DonorNameResponse getDonorNamesByPhoneNumber(int pageNo, int pageSize, String phoneNumber) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<DonorName> result = donorNameRepository.getDonorNamesByPhoneNumber(phoneNumber, pageable);
        List<DonorNameDTO> content = result.getContent().stream()
                .map(dn -> DonorNameMapper.mapToDonorNameDto(dn))
                .collect(Collectors.toList());

        DonorNameResponse response = new DonorNameResponse();
        response.setContent(content);
        response.setPageNo(pageNo + 1);
        response.setPageSize(pageSize);
        response.setTotalElements(result.getTotalElements());
        response.setTotalPages(result.getTotalPages());
        response.setLast(result.isLast());
        return response;
    }

    @Override
    public DonorNameDTO findById(int id) {
        DonorName dn = donorNameRepository.findById(id);
        return DonorNameMapper.mapToDonorNameDto(dn);
    }

}
