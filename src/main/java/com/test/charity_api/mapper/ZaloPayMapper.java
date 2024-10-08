package com.test.charity_api.mapper;

import com.test.charity_api.dto.zalopay.PaymentUrlRequest;
import com.test.charity_api.dto.zalopay.PaymentUrlResponse;
import com.test.charity_api.util.HtmlUtil;
import java.util.HashMap;
import org.json.JSONObject;

public class ZaloPayMapper {

    public static HashMap toPaymentRequestHashMap(PaymentUrlRequest req) {
        return new HashMap() {
            {
                put("campaignId", req.getCampaignId());
                put("phoneNumber", req.getPhoneNumber());
                put("name", HtmlUtil.escapeHTML(req.getName()));
                put("showIdentity", req.isShowIdentity());
                put("amount", req.getAmount());
            }
        };
    }

    public static PaymentUrlRequest toPaymentUrlRequestDto(JSONObject obj) {
        PaymentUrlRequest dto = new PaymentUrlRequest();

        if (obj.has("campaignId")) {
            dto.setCampaignId(obj.getInt("campaignId"));
        }

        if (obj.has("phoneNumber")) {
            dto.setPhoneNumber(obj.getString("phoneNumber"));
        }

        if (obj.has("name")) {
            dto.setName(obj.getString("name"));
        }

        if (obj.has("showIdentity")) {
            dto.setShowIdentity(obj.getBoolean("showIdentity"));
        }

        if (obj.has("amount")) {
            dto.setAmount(obj.getLong("amount"));
        }

        return dto;
    }

    public static PaymentUrlResponse toPaymentUrlResponse(JSONObject obj) {
        PaymentUrlResponse response = new PaymentUrlResponse();

        if (obj.has("return_code")) {
            response.setReturnCode(obj.getInt("return_code"));
        }

        if (obj.has("return_message")) {
            response.setReturnMessage(obj.getString("return_message"));
        }

        if (obj.has("sub_return_code")) {
            response.setSubReturnCode(obj.getInt("sub_return_code"));
        }

        if (obj.has("sub_return_message")) {
            response.setSubReturnMessage(obj.getString("sub_return_message"));
        }

        if (obj.has("zp_trans_token")) {
            response.setZpTransToken(obj.getString("zp_trans_token"));
        }

        if (obj.has("order_url")) {
            response.setOrderUrl(obj.getString("order_url"));
        }

        if (obj.has("order_token")) {
            response.setOrderToken(obj.getString("order_token"));
        }

        return response;
    }
}
