package org.example.controllers;

import org.example.dao.Booking;
import org.example.dao.MatchingRequest;
import org.example.dao.part.Field;
import org.example.dto.*;
import org.example.services.BookingService;
import org.example.services.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tunght.toby.common.security.AuthUserDetails;

import java.util.List;

@RestController
@RequestMapping("/matching")
public class MatchingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private MatchingService matchingService;

    @PostMapping("request")
    public ResponseEntity<MatchingRequest> request(@RequestBody MatchingRestRequest request, @AuthenticationPrincipal AuthUserDetails authUserDetails){
        return new ResponseEntity<MatchingRequest>(matchingService.matchingRequest(request, authUserDetails), HttpStatus.OK);
    }

    @PostMapping("respond")
    public ResponseEntity<Booking> respond(@RequestBody ReponseMatchRequest request, @AuthenticationPrincipal AuthUserDetails authUserDetails){
        return new ResponseEntity<Booking>(matchingService.respondMatchingRequest(request, authUserDetails), HttpStatus.OK);
    }

    @PostMapping("getMatchingRequest")
    public ResponseEntity<List<MatchingRequest>> getMatchingRequest(@RequestBody GetMatchingRequest request){
        return new ResponseEntity<List<MatchingRequest>>(matchingService.getMatchingRequest(request), HttpStatus.OK);
    }

    @PostMapping("delete")
    public ResponseEntity<List<MatchingRequest>> delete(@RequestBody DeleteMatchingRequest request, @AuthenticationPrincipal AuthUserDetails authUserDetails){
        return new ResponseEntity<List<MatchingRequest>>(matchingService.delete(request, authUserDetails), HttpStatus.OK);
    }
}
