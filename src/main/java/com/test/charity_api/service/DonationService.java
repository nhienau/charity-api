package com.test.charity_api.service;

import com.test.charity_api.dto.DonationDTO;
import com.test.charity_api.dto.DonationResponse;
import java.text.ParseException;

public interface DonationService {

    public DonationResponse getDonation(int pageNo, int pageSize, Long campaignId, String name);

    public void insert(DonationDTO d) throws ParseException;
}
