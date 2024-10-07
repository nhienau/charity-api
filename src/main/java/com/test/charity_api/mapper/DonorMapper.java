package com.test.charity_api.mapper;

import com.test.charity_api.dto.DonorDTO;
import com.test.charity_api.entity.Donor;

public class DonorMapper {

    public static DonorDTO mapToDonorDto(Donor d) {
        if (d == null) {
            return null;
        }
        DonorDTO dto = new DonorDTO();
        dto.setId(d.getId());
        dto.setPhoneNumber(d.getPhoneNumber());
        dto.setStatus(d.isStatus());
        return dto;
    }

    public static Donor mapToDonor(DonorDTO dto) {
        if (dto == null) {
            return null;
        }
        Donor d = new Donor();
        d.setId(dto.getId());
        d.setPhoneNumber(dto.getPhoneNumber());
        d.setStatus(dto.isStatus());
        return d;
    }
}
