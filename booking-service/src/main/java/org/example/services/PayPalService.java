package org.example.services;

import org.example.access.PayPalFeignClient;
import org.example.access.model.PayPalAuthResponse;
import org.example.access.model.PayPalPartnerReferralRequest;
import org.example.access.model.PayPalPartnerReferralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayPalService {
    @Autowired
    PayPalFeignClient paypalFeignClient;

    @Value("${paypal.clientId}")
    private String clientId;

    @Value("${paypal.clientSecret}")
    private String clientSecret;

    @Value("${paypal.return.url}")
    private String returnUrl;

    public String getAccessToken() {
        String authorization = "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        PayPalAuthResponse authResponse = paypalFeignClient.getToken(authorization, "client_credentials");
        return authResponse.getAccessToken();
    }

    public PayPalPartnerReferralResponse partnerReferral(){
        String authHeader = "Bearer " + this.getAccessToken();
        PayPalPartnerReferralRequest partnerReferralRequest = new PayPalPartnerReferralRequest();
        partnerReferralRequest.getPartnerConfigOverride().put("return_url", returnUrl);
        return paypalFeignClient.createPartnerReferral(authHeader, partnerReferralRequest);
    }
}