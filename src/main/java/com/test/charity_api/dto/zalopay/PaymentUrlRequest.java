package com.test.charity_api.dto.zalopay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentUrlRequest {

    private int campaignId;
    private String donorId;
    private String phoneNumber;
    private Integer donorNameId;
    private String donorName;
    private boolean showIdentity;
    private long amount;
}
