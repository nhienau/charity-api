package com.test.charity_api.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class DonorDTO {

    private String id;
    private String password;
    private String phoneNumber;
    private String defaultName;
    private boolean status;

    private List<DonationDTO> donation = new ArrayList<>();
    
    private List<RoleDTO> role = new ArrayList<>();

    public DonorDTO() {
    }

    public DonorDTO(String id, String password, String phoneNumber, String defaultName, boolean status) {
        this.id = id;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.defaultName = defaultName;
        this.status = status;
    }
}
