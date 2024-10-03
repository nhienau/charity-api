package com.test.charity_api.controller;

import com.test.charity_api.dto.zalopay.CallbackRequest;
import com.test.charity_api.dto.zalopay.PaymentUrlRequest;
import com.test.charity_api.dto.zalopay.PaymentUrlResponse;
import com.test.charity_api.service.ZaloPayService;
import com.test.charity_api.util.zalopay.Config;
import jakarta.xml.bind.DatatypeConverter;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Mac HmacSHA256;

    @Autowired
    private ZaloPayService zalopayService;

    public PaymentController() throws Exception {
        HmacSHA256 = Mac.getInstance("HmacSHA256");
        HmacSHA256.init(new SecretKeySpec(Config.MERCHANT_INFO.get("key2").getBytes(), "HmacSHA256"));
    }

    @PostMapping("/zalopay/getPaymentUrl")
    public ResponseEntity<PaymentUrlResponse> getPaymentUrl(@RequestBody PaymentUrlRequest params) throws Exception {
        return new ResponseEntity<>(zalopayService.getPaymentUrl(params), HttpStatus.OK);
    }

    @PostMapping("/zalopay/callback")
    public String callback(@RequestBody CallbackRequest callbackData) {
        JSONObject result = new JSONObject();

        try {
            String data = callbackData.getData();
            String reqMac = callbackData.getMac();

            byte[] hashBytes = HmacSHA256.doFinal(data.getBytes());
            String mac = DatatypeConverter.printHexBinary(hashBytes).toLowerCase();

            // kiểm tra callback hợp lệ (đến từ ZaloPay server)
            if (!reqMac.equals(mac)) {
                // callback không hợp lệ
                result.put("return_code", -1);
                result.put("return_message", "mac not equal");
            } else {
                // thanh toán thành công
                // merchant cập nhật trạng thái cho đơn hàng
                JSONObject dataObj = new JSONObject(data);
                String donationInfoStr = dataObj.getString("item");
                // Update

                result.put("return_code", 1);
                result.put("return_message", "success");
            }
        } catch (Exception ex) {
            result.put("return_code", 0);
            result.put("return_message", ex.getMessage());
        }

        // thông báo kết quả cho ZaloPay server
        return result.toString();
    }

}
