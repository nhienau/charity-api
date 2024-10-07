package com.test.charity_api.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class DonorDTO {

    private int id;
    private String phoneNumber;
    private boolean status;

    private List<DonationDTO> donation = new ArrayList<>();

    public DonorDTO() {
    }

    public DonorDTO(int id, String phoneNumber, boolean status) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }
}
