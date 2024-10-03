package com.test.charity_api.service;

import com.test.charity_api.dto.zalopay.PaymentUrlRequest;
import com.test.charity_api.dto.zalopay.PaymentUrlResponse;

public interface ZaloPayService {

    public PaymentUrlResponse getPaymentUrl(PaymentUrlRequest requestBody) throws Exception;

}
