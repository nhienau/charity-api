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
}
