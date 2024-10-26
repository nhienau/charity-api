package com.test.charity_api.service;

import com.test.charity_api.dto.DonorDTO;
import com.test.charity_api.dto.DonorResponse;
import java.util.List;

public interface DonorService {

    public DonorDTO findByPhoneNumber(String phoneNumber);

    public DonorDTO insertDonor(DonorDTO d);

    public List<DonorDTO> getAll();

    public void createDonor(DonorDTO d);

    public void updateDonor(DonorDTO d);

    public void deleteDonor(String id);

    public DonorResponse getDonors(int pageNo, int pageSize, String query);

    public DonorDTO findById(String id);

    public DonorDTO FindUser(String username);

    public void updatePassword(String username, String newPassword) throws Exception;
}
