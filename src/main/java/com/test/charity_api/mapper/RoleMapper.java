package com.test.charity_api.mapper;

import com.test.charity_api.dto.RoleDTO;
import com.test.charity_api.entity.Role;

public class RoleMapper {

    public static RoleDTO mapToRoleDto(Role r) {
        if (r == null) {
            return null;
        }
        return new RoleDTO(r.getId(), r.getName());
    }

    public static Role mapToRole(RoleDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Role(dto.getId(), dto.getName());
    }
}
