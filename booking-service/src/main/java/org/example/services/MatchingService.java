package org.example.services;


import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.example.access.FacilityFeignClient;
import org.example.access.model.GetFacilityByFacilityIdRequest;
import org.example.access.model.GetFacilityByIdResponse;
import org.example.access.model.GetPriceRequest;
import org.example.access.model.GetPriceResponse;
import org.example.dao.Booking;
import org.example.dao.MatchingRequest;
import org.example.dao.part.Field;
import org.example.dao.part.MatchingRequestStatus;
import org.example.dto.*;
import org.example.exception.BaseException;
import org.example.repositories.BookingRepository;
import org.example.repositories.MatchingRequestRepository;
import org.example.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import tunght.toby.common.exception.AppException;
import tunght.toby.common.exception.ErrorCommon;
import tunght.toby.common.security.AuthUserDetails;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MatchingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    MatchingRequestRepository matchingRequestRepository;

    @Autowired
    FacilityFeignClient facilityFeignClient;

    @Autowired
    MongoTemplate mongoTemplate;

//    private final KafkaTemplate<String, CreateBookingEvent> kafkaTemplate;

    public MatchingRequest matchingRequest(MatchingRestRequest request, AuthUserDetails authUserDetails) {
        String requestorId = authUserDetails.getId();
        MatchingRequest matchingRequest = MatchingRequest.builder()
                .bookingId(request.getBookingId())
                .requestorId(requestorId)
                .status(MatchingRequestStatus.PENDING)
                .build();
        MatchingRequest matchingRequestSaved = matchingRequestRepository.save(matchingRequest);
        return matchingRequestSaved;
    }

    public Booking respondMatchingRequest(ReponseMatchRequest request, AuthUserDetails authUserDetails) {
        if(request.getAction()==MatchingRequestStatus.PENDING.value){
            throw new BaseException("PENDING Action is not acceptable");
        }
        MatchingRequest matchingRequest = matchingRequestRepository.findMatchingRequestById(request.getMatchRequestId());
        if (matchingRequest == null) {
            throw new AppException(ErrorCommon.MATCHING_REQUEST_NOT_FOUND);
        }
        Booking booking = bookingRepository.findById(matchingRequest.getBookingId());
        if(!booking.getUserId().equals(authUserDetails.getId())){
            throw new BaseException("This booking is not belong to this user");
        }
        if(request.getAction().equals(MatchingRequestStatus.ACCEPTED.value)){
            matchingRequest.setStatus(MatchingRequestStatus.ACCEPTED);
            booking.setHasOpponent(true);
            booking.setOpponentId(matchingRequest.getRequestorId());
        }
        if(request.getAction().equals(MatchingRequestStatus.DENIED.value)){
            matchingRequest.setStatus(MatchingRequestStatus.DENIED);
        }

        matchingRequestRepository.save(matchingRequest);
        booking = bookingRepository.save(booking);
        return booking;
    }
}
