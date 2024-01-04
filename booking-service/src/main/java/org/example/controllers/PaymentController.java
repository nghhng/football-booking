package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.example.access.model.PayPalPartnerReferralResponse;
import org.example.dao.Booking;
import org.example.dto.CaptureOrderRequest;
import org.example.dto.CreateBookingRequest;
import org.example.services.MatchingService;
import org.example.services.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tunght.toby.common.security.AuthUserDetails;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PayPalService payPalService;
    @Operation(summary = "Api get AccessToken")
    @GetMapping("accessToken")
    public ResponseEntity<String> getAccessToken(){
        return new ResponseEntity<String>(payPalService.getAccessToken(), HttpStatus.OK);
    }

    @Operation(summary = "Api partner referral")
    @GetMapping("partnerReferral")
    public ResponseEntity<PayPalPartnerReferralResponse> partnerReferral(){
        return new ResponseEntity<PayPalPartnerReferralResponse>(payPalService.partnerReferral(), HttpStatus.OK);
    }

    @Operation(summary = "Capture Order")
    @PostMapping("captureOrder")
    public ResponseEntity<Booking> captureOrder(@RequestBody CaptureOrderRequest request){
        return new ResponseEntity<Booking>(payPalService.captureOrder(request.getPaypalOrderId()), HttpStatus.OK);
    }
}
