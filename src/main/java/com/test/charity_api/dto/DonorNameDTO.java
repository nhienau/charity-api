package com.test.charity_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonorNameDTO {

    private int id;
    private String name;
    private DonorDTO donor;

}