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
    private String phoneNumber;
    private String name;
    private boolean showIdentity;
    private long amount;
}
