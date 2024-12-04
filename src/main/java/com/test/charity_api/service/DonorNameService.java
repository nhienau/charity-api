package com.test.charity_api.service;

import com.test.charity_api.dto.DonorNameDTO;
import com.test.charity_api.dto.DonorNameResponse;

public interface DonorNameService {

    public DonorNameDTO findByNameAndDonorId(String name, String donorId);

    public DonorNameDTO insert(DonorNameDTO dn);

    public DonorNameResponse getDonorNamesByPhoneNumber(int pageNo, int pageSize, String phoneNumber);

    public DonorNameDTO findById(int id);
}
