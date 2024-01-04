package org.example.access;

import feign.Headers;
import feign.QueryMap;
import feign.RequestLine;
import org.example.access.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("${paypal.create.order.endpoint}")
    PayPalCreateOrderResponse createOrder(@RequestHeader("Authorization") String authorization,
                                          @RequestHeader("PayPal-Partner-Attribution-Id") String partnerAttributeId,
                                          @RequestHeader("PayPal-Auth-Assertion") String authAssertion,
                                          @RequestBody PayPalCreateOrderRequest request);

    @PostMapping("/v2/checkout/orders/{orderId}/capture")
    PayPalCaptureOrderResponse captureOrder(@RequestHeader("Authorization") String authorization,
                                           @RequestHeader("PayPal-Partner-Attribution-Id") String partnerAttributeId,
                                           @RequestHeader("PayPal-Auth-Assertion") String authAssertion,
                                            @PathVariable("orderId") String orderId,
                                            @RequestBody PayPalCreateOrderRequest request);

}