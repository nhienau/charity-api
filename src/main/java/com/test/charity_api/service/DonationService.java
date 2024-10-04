package com.test.charity_api.service;

import com.test.charity_api.dto.DonationDTO;
import java.util.List;

public interface DonationService {

    public List<DonationDTO> getDonation(Long campaignId);

    public void insert(DonationDTO d);
}
