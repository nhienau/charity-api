package com.test.charity_api.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class DonorNameDTO {

    private int id;
    private String name;
    private DonorDTO donor;

    private List<DonationDTO> donation = new ArrayList<>();

    public DonorNameDTO() {

    }

    public DonorNameDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
