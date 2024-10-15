package com.test.charity_api.mapper;

import com.test.charity_api.dto.DonorDTO;
import com.test.charity_api.entity.Donor;
import java.util.stream.Collectors;

public class DonorMapper {

    public static DonorDTO mapToDonorDto(Donor d) {
        if (d == null) {
            return null;
        }
        DonorDTO dto = new DonorDTO();
        dto.setId(d.getId());
        dto.setPhoneNumber(d.getPhoneNumber());
        dto.setDefaultName(d.getDefaultName());
        dto.setRole(d.getRoles().stream().map(r -> RoleMapper.mapToRoleDto(r)).collect(Collectors.toList()));
        dto.setStatus(d.isStatus());
        return dto;
    }

    public static DonorDTO mapToDonorDto(Donor d, boolean mapName) {
        if (d == null) {
            return null;
        }
        DonorDTO dto = new DonorDTO();
        if (mapName) {
            dto.setDefaultName(d.getDefaultName());
        }
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
        d.setDefaultName(dto.getDefaultName());
        d.setRoles(dto.getRole().stream().map(r -> RoleMapper.mapToRole(r)).collect(Collectors.toList()));
        d.setStatus(dto.isStatus());
        return d;
    }
}
