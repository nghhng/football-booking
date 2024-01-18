package org.example.services;


import lombok.RequiredArgsConstructor;
import org.example.access.FacilityFeignClient;
import org.example.access.UserFeignClient;
import org.example.access.model.*;
import org.example.dao.Booking;
import org.example.dao.Facility;
import org.example.dao.MatchingRequest;
import org.example.dao.User;
import org.example.dao.part.MatchingRequestStatus;
import org.example.dto.*;
import org.example.exception.BaseException;
import org.example.repositories.BookingRepository;
import org.example.repositories.MatchingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import nghhng.common.enums.ENotifications;
import nghhng.common.exception.AppException;
import nghhng.common.exception.ErrorCommon;
import nghhng.common.security.AuthUserDetails;
import nghhng.common.utils.JsonConverter;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    MatchingRequestRepository matchingRequestRepository;

    @Autowired
    FacilityFeignClient facilityFeignClient;

    @Autowired
    UserFeignClient userFeignClient;

    @Autowired
    MongoTemplate mongoTemplate;

    @Value(value = "${kafka.request-noti-topic}")
    private String requestNotiTopoc;

    private final KafkaTemplate<String, Object> notiKafkaTemplate;

//    private final KafkaTemplate<String, CreateBookingEvent> kafkaTemplate;

    public MatchingRequest matchingRequest(MatchingRestRequest request, AuthUserDetails authUserDetails) {
        String requestorId = authUserDetails.getId();
        Booking booking = bookingRepository.findById(request.getBookingId());
        Facility facility = facilityFeignClient.getFacilityById(new GetFacilityByFacilityIdRequest(booking.getFacilityId()));
        User hostUser = userFeignClient.getUserById(new GetUserByIdRequest(booking.getUserId()));
        User requestor = userFeignClient.getUserById(new GetUserByIdRequest(authUserDetails.getId()));
        if (booking.getUserId().equals(requestorId))
            throw new BaseException("You can not request your own booking");
        GetMatchingRequest checkRequestFilter = GetMatchingRequest.builder()
                .bookingId(request.getBookingId())
                .requestorId(authUserDetails.getId())
                .build();
        List<MatchingRequest> checkRequests = getMatchingRequest(checkRequestFilter);
        if(!checkRequests.isEmpty()){
            throw new BaseException("You already requested this booking");
        }
        MatchingRequest matchingRequest = MatchingRequest.builder()
                .bookingId(request.getBookingId())
                .facilityName(facility.getName())
                .startAt(booking.getStartAt())
                .endAt(booking.getEndAt())
                .date(booking.getDate())
                .price(booking.getPrice())
                .requestorId(requestorId)
                .requestorImage(requestor.getImage())
                .requestorName(requestor.getName())
                .hostUserId(booking.getUserId())
                .hostUserImage(hostUser.getImage())
                .hostUserName(hostUser.getName())
                .status(MatchingRequestStatus.PENDING)
                .build();
        MatchingRequest matchingRequestSaved = matchingRequestRepository.save(matchingRequest);

        NotificationDto notificationDto = NotificationDto.builder()
                .type(ENotifications.REQUEST)
                .fromUserId(authUserDetails.getId())
                .toUserId(booking.getUserId())
                .detailId(booking.getId())
                .message(createRequestNotiMessage(requestor, booking))
                .isRead(false)
                .timeStamp(Instant.now())
                .build();
        System.out.println("SEND KAFKA: " + notificationDto.toString());
        notiKafkaTemplate.send(requestNotiTopoc, JsonConverter.serializeObject(notificationDto));

        return matchingRequestSaved;
    }

    private String createRequestNotiMessage(User user, Booking booking){
        String form = "%s đã gửi lời mời ghép đối trận bóng sân số %s, %s, thời gian: %s-%s, %s,";
        return String.format(form, user.getUsername(), booking.getFieldIndex(), booking.getFacilityName(),  booking.getStartAt().toString(), booking.getEndAt().toString(), booking.getDate());
    }

    public Booking respondMatchingRequest(ReponseMatchRequest request, AuthUserDetails authUserDetails) {
        if (request.getAction() == MatchingRequestStatus.PENDING.value) {
            throw new BaseException("PENDING Action is not acceptable");
        }
        MatchingRequest matchingRequest = matchingRequestRepository.findMatchingRequestById(request.getMatchRequestId());
        if (matchingRequest == null) {
            throw new AppException(ErrorCommon.MATCHING_REQUEST_NOT_FOUND);
        }
        if (!matchingRequest.getStatus().equals(MatchingRequestStatus.PENDING)) {
            throw new BaseException("This request is not PENDING");
        }
        Booking booking = bookingRepository.findById(matchingRequest.getBookingId());
        if (!matchingRequest.getHostUserId().equals(authUserDetails.getId())) {
            throw new BaseException("This booking is not belong to this user");
        }
        if (booking.isHasOpponent()==false) {
            throw new BaseException("This booking is not looking for opponent");
        }
        if (request.getAction().equals(MatchingRequestStatus.ACCEPTED.value)) {
            matchingRequest.setStatus(MatchingRequestStatus.ACCEPTED);
            booking.setHasOpponent(false);
            booking.setOpponentId(matchingRequest.getRequestorId());
            List<MatchingRequest> allRequests = matchingRequestRepository.findMatchingRequestByBookingId(matchingRequest.getBookingId());
            allRequests.stream()
                    .filter(oneRequest -> !oneRequest.getRequestorId().equals(authUserDetails.getId()))
                    .forEach(oneRequest -> {
                        oneRequest.setStatus(MatchingRequestStatus.DENIED);
                        matchingRequestRepository.save(oneRequest);
                    });
        } else if (request.getAction().equals(MatchingRequestStatus.DENIED.value)) {
            matchingRequest.setStatus(MatchingRequestStatus.DENIED);
        } else
            throw new BaseException("Action không đúng, phải là ACCEPTED hoặc DENIED");

        matchingRequestRepository.save(matchingRequest);
        booking = bookingRepository.save(booking);
        return booking;
    }

    public List<MatchingRequest> getMatchingRequest(GetMatchingRequest getMatchingRequest) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        java.lang.reflect.Field[] fields = getMatchingRequest.getClass().getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(getMatchingRequest);
                if (value == null) {
                    continue;
                } else {
                    if (field.getName().equals("limit")) {
                        query.limit(getMatchingRequest.getLimit());
                    } else if (field.getName().equals("skip")) {
                        query.skip(getMatchingRequest.getSkip());
                    } else
                        criteria.and(field.getName()).is(value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        query.addCriteria(criteria);

        List<MatchingRequest> matchingRequests = mongoTemplate.find(query, MatchingRequest.class);
        return matchingRequests;
    }

    public List<MatchingRequest> delete(DeleteMatchingRequest deleteMatchingRequest, AuthUserDetails authUserDetails) {
        MatchingRequest matchingRequest = matchingRequestRepository.findMatchingRequestById(deleteMatchingRequest.getId());
        if (matchingRequest == null) {
            throw new AppException(ErrorCommon.MATCHING_REQUEST_NOT_FOUND);
        }
        if (!matchingRequest.getStatus().equals(MatchingRequestStatus.PENDING)) {
            throw new BaseException("Can only delete PENDING request");
        }
        if (!matchingRequest.getRequestorId().equals(authUserDetails.getId())) {
            throw new BaseException("This request is not belong to you");
        }
        List<MatchingRequest> deletedRequests = matchingRequestRepository.deleteMatchingRequestById(deleteMatchingRequest.getId());
        return deletedRequests;
    }
}