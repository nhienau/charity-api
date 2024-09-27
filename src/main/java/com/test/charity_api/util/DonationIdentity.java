package com.test.charity_api.util;

import com.test.charity_api.dto.DonationDTO;
import java.util.List;

public class DonationIdentity {

    public static List<DonationDTO> toggleDonationIdentity(List<DonationDTO> list) {
        for (DonationDTO d : list) {
            if (!d.isShowIdentity()) {
                d.getDonor().setName("Nhà hảo tâm");
                d.getDonor().setId(-1);
            }
        }
        return list;
    }
}
