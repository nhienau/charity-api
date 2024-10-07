package com.test.charity_api.mapper;

import com.test.charity_api.dto.DonorNameDTO;
import com.test.charity_api.entity.DonorName;

public class DonorNameMapper {

    public static DonorNameDTO mapToDonorNameDto(DonorName dn) {
        if (dn == null) {
            return null;
        }
        DonorNameDTO dto = new DonorNameDTO();
        dto.setId(dn.getId());
        dto.setName(dn.getName());
        return dto;
    }

    public static DonorName mapToDonorName(DonorNameDTO dto) {
        if (dto == null) {
            return null;
        }
        DonorName dn = new DonorName();
        dn.setId(dto.getId());
        dn.setName(dto.getName());
        dn.setDonor(DonorMapper.mapToDonor(dto.getDonor()));
        return dn;
    }
}
