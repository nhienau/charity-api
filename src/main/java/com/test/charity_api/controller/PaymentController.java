package com.test.charity_api.controller;

import com.test.charity_api.dto.CampaignDTO;
import com.test.charity_api.dto.DonationDTO;
import com.test.charity_api.dto.DonorDTO;
import com.test.charity_api.dto.DonorNameDTO;
import com.test.charity_api.dto.zalopay.CallbackRequest;
import com.test.charity_api.dto.zalopay.PaymentUrlRequest;
import com.test.charity_api.dto.zalopay.PaymentUrlResponse;
import com.test.charity_api.mapper.ZaloPayMapper;
import com.test.charity_api.service.CampaignService;
import com.test.charity_api.service.DonationService;
import com.test.charity_api.service.DonorNameService;
import com.test.charity_api.service.DonorService;
import com.test.charity_api.service.ZaloPayService;
import jakarta.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final String zalopayKey2;

    @Autowired
    private ZaloPayService zalopayService;

    @Autowired
    private DonorService donorService;

    @Autowired
    private DonorNameService donorNameService;

    @Autowired
    private DonationService donationService;

    @Autowired
    private CampaignService campaignService;

    public PaymentController(@Value("${zalopay.key2}") String zalopayKey2) throws Exception {
        this.zalopayKey2 = zalopayKey2;
        HmacSHA256 = Mac.getInstance("HmacSHA256");
        HmacSHA256.init(new SecretKeySpec(this.zalopayKey2.getBytes(), "HmacSHA256"));
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
                JSONArray jsonArr = new JSONArray(donationInfoStr);
                JSONObject jsonObj = jsonArr.getJSONObject(0);
                long serverTime = dataObj.getLong("server_time");
                String transactionId = dataObj.getString("app_trans_id");
                PaymentUrlRequest req = ZaloPayMapper.toPaymentUrlRequestDto(jsonObj);
                handleNewDonation(req, serverTime, transactionId);

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

    public void handleNewDonation(PaymentUrlRequest data, long serverTime, String transactionId) {
        int campaignId = data.getCampaignId();
        String phoneNumber = data.getPhoneNumber();
        String name = data.getName();
        boolean showIdentity = data.isShowIdentity();
        long amount = data.getAmount();

        DonorDTO donor = donorService.findByPhoneNumber(phoneNumber);
        if (donor == null) {
            DonorDTO temp = new DonorDTO();
            temp.setPhoneNumber(phoneNumber);
            temp.setStatus(true);
            donor = donorService.insertDonor(temp);
        }

        int donorId = donor.getId();
        DonorNameDTO donorName = null;
        if (showIdentity) {
            donorName = donorNameService.findByNameAndDonorId(name, donorId);
            if (donorName == null) {
                DonorNameDTO temp = new DonorNameDTO();
                temp.setName(name);
                temp.setDonor(donor);
                donorName = donorNameService.insert(temp);
            }
        }

        CampaignDTO campaign = campaignService.findById(campaignId);

        DonationDTO donation = new DonationDTO();
        donation.setCampaign(campaign);
        donation.setDonor(donor);
        donation.setAmount(amount);
        Date createdAt = new Date(serverTime);
        donation.setCreatedAt(createdAt);
        donation.setTransactionId(transactionId);
        donation.setDonorNameId(showIdentity ? donorName.getId() : null);
        donationService.insert(donation);
        campaignService.updateDonation(campaignId, amount);
    }
}
