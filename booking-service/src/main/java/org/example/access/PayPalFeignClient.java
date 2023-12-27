package org.example.access;

import feign.Headers;
import feign.QueryMap;
import org.example.access.model.PayPalAuthResponse;
import org.example.access.model.PayPalPartnerReferralRequest;
import org.example.access.model.PayPalPartnerReferralResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "paypal", url = "${paypal.base.url}")
public interface PayPalFeignClient {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @PostMapping("${paypal.auth.endpoint}")
    PayPalAuthResponse getToken(@RequestHeader("Authorization") String authorization,
                                @RequestParam("grant_type") String grantType);
    @PostMapping("${paypal.referral.endpoint}")
    PayPalPartnerReferralResponse createPartnerReferral(@RequestHeader("Authorization") String authorization,
                                                        @RequestBody PayPalPartnerReferralRequest request);

}