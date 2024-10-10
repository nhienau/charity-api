package com.test.charity_api.service;

import com.test.charity_api.dto.DonorNameDTO;

public interface DonorNameService {

    public DonorNameDTO findByNameAndDonorId(String name, String donorId);

    public DonorNameDTO insert(DonorNameDTO dn);
}
