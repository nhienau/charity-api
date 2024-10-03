package com.test.charity_api.dto.zalopay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentUrlResponse {

    private int returnCode;
    private String returnMessage;
    private int subReturnCode;
    private String subReturnMessage;
    private String zpTransToken;
    private String orderUrl;
    private String orderToken;
}
