package com.test.charity_api.dto.zalopay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CallbackRequest {

    private String data;
    private String mac;
    private int type;
}
