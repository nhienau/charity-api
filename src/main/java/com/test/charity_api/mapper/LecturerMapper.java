package com.test.charity_api.mapper;

import com.test.charity_api.dto.LecturerDTO;
import com.test.charity_api.entity.Lecturer;

public class LecturerMapper {

    public static LecturerDTO mapToLecturerDto(Lecturer l) {
        if (l == null) {
            return null;
        }
        LecturerDTO dto = new LecturerDTO();
        dto.setId(l.getId());
        dto.setName(l.getName());
        dto.setStatus(l.isStatus());
        return dto;
    }

    public static Lecturer mapToLecturer(LecturerDTO dto) {
        if (dto == null) {
            return null;
        }
        Lecturer l = new Lecturer();
        l.setId(dto.getId());
        l.setName(dto.getName());
        l.setStatus(dto.isStatus());
        return l;
    }
}
