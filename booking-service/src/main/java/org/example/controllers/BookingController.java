package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.example.access.model.PayPalCreateOrderResponse;
import org.example.dao.Booking;
import org.example.dto.*;
import org.example.services.BookingService;
import org.example.services.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import nghhng.common.security.AuthUserDetails;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private MatchingService matchingService;

    @Operation(summary = "Api create booking")
    @PostMapping("createBooking")
    public ResponseEntity<PayPalCreateOrderResponse> createBooking(@RequestBody CreateBookingRequest createBookingRequest, @AuthenticationPrincipal AuthUserDetails authUserDetails){
        return new ResponseEntity<PayPalCreateOrderResponse>(bookingService.createBooking(createBookingRequest, authUserDetails), HttpStatus.OK);
    }

    @Operation(summary = "Api create booking by owner")
    @PostMapping("createBookingByOwner")
    public ResponseEntity<Booking> createBookingByOwner(@RequestBody CreateBookingRequest createBookingRequest, @AuthenticationPrincipal AuthUserDetails authUserDetails){
        return new ResponseEntity<Booking>(bookingService.createBookingByOwner(createBookingRequest, authUserDetails), HttpStatus.OK);
    }

    @PostMapping("getBooking")
    public ResponseEntity<GetBookingResponse> getBooking(@RequestBody GetBookingRequest getBookingRequest){
        return new ResponseEntity<GetBookingResponse>(bookingService.getBooking(getBookingRequest), HttpStatus.OK);
    }

    @PostMapping("getAvailableFields")
    public ResponseEntity<List<GetAvailableFieldsResponse>> getAvalableFields(@RequestBody GetAvailableFieldsRequest getAvailableFieldsRequest){
        return new ResponseEntity<List<GetAvailableFieldsResponse>>(bookingService.getAvailableFieldsByTimeAndDayAndFacility(getAvailableFieldsRequest), HttpStatus.OK);
    }

    @PostMapping("switchStatus")
    public ResponseEntity<Booking> switchStatus(@RequestBody SwitchStatusRequest request){
        return new ResponseEntity<Booking>(bookingService.switchStatus(request), HttpStatus.OK);
    }
}
