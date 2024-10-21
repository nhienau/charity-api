package com.test.charity_api.service;

import com.test.charity_api.dto.DonorDTO;

public interface DonorService {

    public DonorDTO findByPhoneNumber(String phoneNumber);

    public DonorDTO insertDonor(DonorDTO d);

    public DonorDTO findById(String id);
    
    public void updatePassword(String username, String newPassword) throws Exception;
}
