package org.example.services;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.example.access.PayPalFeignClient;
import org.example.access.UserFeignClient;
import org.example.access.model.*;
import org.example.dao.Booking;
import org.example.dao.User;
import org.example.dto.NotificationDto;
import org.example.exception.BaseException;
import org.example.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;
import tunght.toby.common.security.AuthUserDetails;
import tunght.toby.common.utils.JsonConverter;

import java.awt.print.Book;
import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
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

    @Autowired
    UserFeignClient userFeignClient;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    private final RedisTemplate<String, String> redisTemplate;

    @Value(value = "${kafka.booking-noti-topic}")
    private String bookingNotiTopic;

    private final KafkaTemplate<String, Object> notiKafkaTemplate;
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
        String orderTitle = "Đặt sân số %s, cơ sở %s, thời gian: %s-%s, %s";
        orderTitle = String.format(orderTitle, booking.getFieldIndex(), booking.getFacilityName(), booking.getStartAt().toString(), booking.getEndAt().toString(), booking.getDate());
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

    public Booking captureOrder(String paypalOrderId){
        var jsonStr = redisTemplate.opsForValue().get(paypalOrderId);
        String[] parts = jsonStr.split("\\|");
        Booking booking = JsonConverter.deserializeObject(parts[0], Booking.class);
        String sellerMerchantId = parts[1];
        String authHeader = "Bearer " + this.getAccessToken();
        String authAssertion = getAuthAssertionValue(clientId, sellerMerchantId);
        PayPalCreateOrderRequest request = new PayPalCreateOrderRequest();
        PayPalCaptureOrderResponse response = paypalFeignClient.captureOrder(authHeader, BNCode, authAssertion, paypalOrderId, request);
        if(response.getStatus().equals("COMPLETED")){
            bookingRepository.save(booking);
            System.out.println("SEND KAFKA: " + parts[2]);
            notiKafkaTemplate.send(bookingNotiTopic, parts[2]);

        } else{
            throw new BaseException("Capture Order failed");
        }
        return booking;
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