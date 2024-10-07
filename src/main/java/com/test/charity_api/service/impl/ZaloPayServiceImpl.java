package com.test.charity_api.service.impl;

import com.test.charity_api.dto.zalopay.PaymentUrlRequest;
import com.test.charity_api.dto.zalopay.PaymentUrlResponse;
import com.test.charity_api.mapper.ZaloPayMapper;
import com.test.charity_api.service.ZaloPayService;
import com.test.charity_api.util.DateTimeUtil;
import com.test.charity_api.util.zalopay.HMACUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ZaloPayServiceImpl implements ZaloPayService {

    @Value("${zalopay.redirect-base-url}")
    private String redirectBaseUrl;
    @Value("${zalopay.app-id}")
    private String appId;
    @Value("${zalopay.callback-url}")
    private String callbackUrl;
    @Value("${zalopay.key1}")
    private String key1;
    @Value("${zalopay.endpoint}")
    private String endpoint;

    @Override
    public PaymentUrlResponse getPaymentUrl(PaymentUrlRequest requestBody) throws Exception {
        Random rand = new Random();
        int random_id = rand.nextInt(1000000);
        final Map embed_data = new HashMap() {
            {
                put("redirecturl", redirectBaseUrl + "/" + requestBody.getCampaignId());
            }
        };

        final Map[] item = {
            ZaloPayMapper.toPaymentRequestHashMap(requestBody)
        };

        JSONArray jsonItem = new JSONArray(item);

        Map<String, Object> order = new HashMap<String, Object>() {
            {
                put("app_id", appId);
                put("app_trans_id", DateTimeUtil.getCurrentTimeString("yyMMdd") + "_" + random_id); // translation missing: vi.docs.shared.sample_code.comments.app_trans_id
                put("app_time", System.currentTimeMillis()); // miliseconds
                put("app_user", "app_user");
                put("amount", requestBody.getAmount());
                put("description", "Quyen gop tu thien SGU #" + random_id);
                put("bank_code", "");
                put("item", jsonItem);
                put("embed_data", new JSONObject(embed_data).toString());
                put("callback_url", callbackUrl);
                put("title", "Quyen gop tu thien SGU #" + random_id);
            }
        };

        // app_id +”|”+ app_trans_id +”|”+ appuser +”|”+ amount +"|" + app_time +”|”+ embed_data +"|" +item
        String data = order.get("app_id") + "|" + order.get("app_trans_id") + "|" + order.get("app_user") + "|" + order.get("amount")
                + "|" + order.get("app_time") + "|" + order.get("embed_data") + "|" + order.get("item");
        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key1, data));

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(endpoint);

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : order.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }

        // Content-Type: application/x-www-form-urlencoded
        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }

        JSONObject result = new JSONObject(resultJsonStr.toString());
        return ZaloPayMapper.toPaymentUrlResponse(result);
    }

}
