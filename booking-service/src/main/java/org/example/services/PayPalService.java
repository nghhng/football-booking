package org.example.services;

import org.apache.kafka.common.protocol.types.Field;
import org.example.access.PayPalFeignClient;
import org.example.access.model.*;
import org.example.dao.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Value("${paypal.BNCode}")
    private String BNCode;

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

    public PayPalCreateOrderResponse createPayPalOrder(Booking booking, String sellerId){
        String authHeader = "Bearer " + this.getAccessToken();
        PayPalCreateOrderRequest request = buildCreateOrderRequest(booking);
        String authAssertion = getAuthAssertionValue(clientId, sellerId);
        return paypalFeignClient.createOrder(authHeader, BNCode, authAssertion, request);
    }
    public PayPalCreateOrderRequest buildCreateOrderRequest(Booking booking){
        String amountUSD = convertVNDtoUSD(booking.getPrice());
        String orderTitle = "Booking-Field " + booking.getFieldIndex() + ", facility: " + booking.getFacilityName();
        String returnURL = "http://localhost:3030/profile/"+ booking.getUserId() +"?tab=my-booking";

        PayPalCreateOrderRequest request = new PayPalCreateOrderRequest();
        request.setIntent("CAPTURE");

        List<PayPalCreateOrderRequest.PurchaseUnit> purchaseUnits = new ArrayList<>();
        PayPalCreateOrderRequest.PurchaseUnit purchaseUnit = new PayPalCreateOrderRequest.PurchaseUnit();

        List<PayPalCreateOrderRequest.Item> items = new ArrayList<>();
        PayPalCreateOrderRequest.Item item = new PayPalCreateOrderRequest.Item(orderTitle, orderTitle, "1", new PayPalCreateOrderRequest.UnitAmount("USD", amountUSD));
        items.add(item);

        purchaseUnit.setItems(items);

        PayPalCreateOrderRequest.Amount amount = new PayPalCreateOrderRequest.Amount("USD", amountUSD, new PayPalCreateOrderRequest.Breakdown(new PayPalCreateOrderRequest.UnitAmount("USD", amountUSD)));
        purchaseUnit.setAmount(amount);

        purchaseUnits.add(purchaseUnit);

        PayPalCreateOrderRequest.ApplicationContext applicationContext = new PayPalCreateOrderRequest.ApplicationContext(returnURL);
        request.setPurchase_units(purchaseUnits);
        request.setApplication_context(applicationContext);

        return request;
    }

    private static String getAuthAssertionValue(String clientId, String sellerPayerId) {
        String header = "{\"alg\":\"none\"}";
        String encodedHeader = base64url(header);

        String payload = "{\"iss\":\"" + clientId + "\",\"payer_id\":\"" + sellerPayerId + "\"}";
        String encodedPayload = base64url(payload);

        return encodedHeader + "." + encodedPayload + ".";
    }

    private static String base64url(String json) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(json.getBytes())
                .replace("+", "-")
                .replace("/", "_");
    }

    public static String convertVNDtoUSD(String amountInVND) {
        double exchangeRate = 24300;

        double amount = Double.parseDouble(amountInVND);

        // Chuyển đổi và làm tròn đến số nguyên
        double amountInUSD = Math.round(amount / exchangeRate);

        return Double.toString(amountInUSD);
    }
}