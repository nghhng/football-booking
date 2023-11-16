package org.example.controllers;

import org.example.access.Field;
import org.example.dao.Booking;
import org.example.dto.CreateBookingRequest;
import org.example.dto.GetAvailableFieldsRequest;
import org.example.dto.GetBookingRequest;
import org.example.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @LoadBalanced
    @PostMapping("createBooking")
    public ResponseEntity<Booking> createBooking(@RequestBody CreateBookingRequest createBookingRequest){
        return new ResponseEntity<Booking>(bookingService.createBooking(createBookingRequest), HttpStatus.OK);
    }

    @PostMapping("getBooking")
    public ResponseEntity<List<Booking>> getBooking(@RequestBody GetBookingRequest getBookingRequest){
        return new ResponseEntity<List<Booking>>(bookingService.getBooking(getBookingRequest), HttpStatus.OK);
    }

    @PostMapping("getAvailableFields")
    public ResponseEntity<List<Field>> getAvalableFields(@RequestBody GetAvailableFieldsRequest getAvailableFieldsRequest){
        return new ResponseEntity<List<Field>>(bookingService.getAvailableFieldsByTimeAndDayAndFacility(getAvailableFieldsRequest), HttpStatus.OK);
    }
}
