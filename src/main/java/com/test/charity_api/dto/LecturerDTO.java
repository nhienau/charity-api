package com.test.charity_api.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class LecturerDTO {

    private int id;
    private String name;
    private boolean status;

    private List<CampaignDTO> campaign = new ArrayList<>();

    public LecturerDTO() {
    }

    public LecturerDTO(int id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}
