package org.example.services;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.example.access.*;
import org.example.access.model.*;
import org.example.dao.Booking;
import org.example.dao.Facility;
import org.example.dao.MatchingRequest;
import org.example.dao.User;
import org.example.dao.part.Field;
import org.example.dao.part.MatchingRequestStatus;
import org.example.dto.*;
import org.example.exception.BaseException;
import org.example.repositories.BookingRepository;
import org.example.repositories.MatchingRequestRepository;
import org.example.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import tunght.toby.common.enums.ENotifications;
import tunght.toby.common.exception.AppException;
import tunght.toby.common.exception.ErrorCommon;
import tunght.toby.common.security.AuthUserDetails;
import tunght.toby.common.utils.JsonConverter;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    @Value(value = "${kafka.booking-noti-topic}")
    private String bookingNotiTopic;

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

    private final KafkaTemplate<String, Object> notiKafkaTemplate;




    public Booking createBooking(CreateBookingRequest createBookingRequest, AuthUserDetails authUserDetails ){

        GetFacilityByFacilityIdRequest getFacilityByFacilityIdRequest = new GetFacilityByFacilityIdRequest(createBookingRequest.getFacilityId());
        //check Facility and maximum number of fields
        Facility bookedFacility = facilityFeignClient.getFacilityById(getFacilityByFacilityIdRequest);
        if(bookedFacility==null){
            throw new BaseException("Facility not exists");
        }
//        for(Field field : response.getFields()){
//            if(field.getType().equals(createBookingRequest.getFieldType())){
//                if(Integer.parseInt(field.getNumber())<createBookingRequest.getNumberOfFields()){
//                    throw new BaseException("Not enough fields to book");
//                } else break;
//            }
//        }
        //Check Booking
        GetBookingRequest getBookingRequest = GetBookingRequest.builder()
                .facilityId(createBookingRequest.getFacilityId())
                .fieldIndex(createBookingRequest.getFieldIndex())
                .startAt(createBookingRequest.getStartAt())
                .endAt(createBookingRequest.getEndAt())
                .date(createBookingRequest.getDate())
                .build();
        List<Booking> booked = getBooking(getBookingRequest);
        if(booked.size()!=0){
            throw new BaseException("This field is already booked at this timeslot");
        }
        //get Type of selected field
        String bookingType = new String();
        Optional<Field> selectedField = bookedFacility.getFields().stream()
                .filter(field -> createBookingRequest.getFieldIndex().equals(field.getIndex()))
                .findFirst();
        if(selectedField.isPresent()){
            bookingType = selectedField.get().getType();
        } else {
            throw new BaseException("Can not find the field with given index");
        }


        //get Price
        GetPriceRequest getPriceRequest = new GetPriceRequest(createBookingRequest.getFacilityId(),bookingType , createBookingRequest.getStartAt(), createBookingRequest.getEndAt());
        List<GetPriceResponse> getPriceResponses = facilityFeignClient.getPrice(getPriceRequest);

        String price = new String();
        //check the date is weekend of not then calculate price
        if(!Utils.isWeekend(createBookingRequest.getDate())){
            price = String.valueOf(getPriceResponses.get(0).getAmount());
        } else{
            price = String.valueOf(getPriceResponses.get(0).getSpecialAmount());
        }


        String priceId = getPriceResponses.get(0).getId();
        //select Field
        //now set Field index random
//        List<BookField> bookFields = new ArrayList<BookField>();
//        for(int i=0; i<createBookingRequest.getNumberOfFields(); i++){
//            BookField bookField = new BookField();
//            bookField.setIndex(String.valueOf(i));
//            bookField.setType(createBookingRequest.getFieldType());
//            bookFields.add(bookField);
//        }

        Booking booking = Booking.builder()
                .facilityId(createBookingRequest.getFacilityId())
                .userId(authUserDetails.getId())
                .date(createBookingRequest.getDate())
                .priceId(priceId)
                .price(price)
                .startAt(createBookingRequest.getStartAt())
                .endAt(createBookingRequest.getEndAt())
                .hasOpponent(false)
                .fieldIndex(createBookingRequest.getFieldIndex())
                .build();

        booking = bookingRepository.save(booking);

        User user = userFeignClient.getUserById(new GetUserByIdRequest(booking.getUserId()));


        NotificationDto notificationDto = NotificationDto.builder()
                .type(ENotifications.BOOKING)
                .fromUserId(authUserDetails.getId())
                .toUserId(bookedFacility.getOwnerId())
                .bookingId(booking.getId())
                .message(ENotifications.getNotificationMessage(ENotifications.BOOKING, user.getUsername()))
                .isRead(false)
                .build();

//        notiKafkaTemplate.send(bookingNotiTopic, JsonConverter.serializeObject(notificationDto));
        return  booking;
    }

    public List<Booking> getBooking(GetBookingRequest getBookingRequest){
        Query query = new Query();
        Criteria criteria = new Criteria();
        java.lang.reflect.Field[] fields = getBookingRequest.getClass().getDeclaredFields();
        for(java.lang.reflect.Field field : fields){
            field.setAccessible(true);
            try {
                Object value = field.get(getBookingRequest);
                if(value==null) {
                    continue;
                }
                else {
                    if(field.getName().equals("limit")){
                        query.limit(getBookingRequest.getLimit());
                    } else if(field.getName().equals("skip")){
                        query.skip(getBookingRequest.getSkip());
                    } else if(field.getName().equals("hasOpponent")){
                        if(value.equals("true"))
                            criteria.and(field.getName()).is(true);
                        else if(value.equals("false"))
                            criteria.and(field.getName()).is(false);
                    } else
                    criteria.and(field.getName()).is(value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        query.addCriteria(criteria);

        List<Booking> bookings = mongoTemplate.find(query, Booking.class);
        return bookings;
    }

    public List<GetAvailableFieldsResponse> getAvailableFieldsByTimeAndDayAndFacility(GetAvailableFieldsRequest getAvailableFieldsRequest){
        GetBookingRequest getPlacedBookingRequest = GetBookingRequest.builder()
                .facilityId(getAvailableFieldsRequest.getFacilityId())
                .startAt(getAvailableFieldsRequest.getStartAt())
                .endAt(getAvailableFieldsRequest.getEndAt())
                .date(getAvailableFieldsRequest.getDate())
                .build();
        List<Booking> placedBookings = this.getBooking(getPlacedBookingRequest);

        //check Facility and maximum number of fields
        GetFacilityByFacilityIdRequest getFacilityByFacilityIdRequest = new GetFacilityByFacilityIdRequest(getAvailableFieldsRequest.getFacilityId());

        Facility response = facilityFeignClient.getFacilityById(getFacilityByFacilityIdRequest);
        if(response==null){
            throw new BaseException("Facility not exists");
        }
        //Get prices
        GetPriceRequest getPriceRequest = new GetPriceRequest(getAvailableFieldsRequest.getFacilityId(),null, getAvailableFieldsRequest.getStartAt(), getAvailableFieldsRequest.getEndAt());
        List<GetPriceResponse> priceList = facilityFeignClient.getPrice(getPriceRequest);
        //Check weekend
        Map<String, Integer> typeToAmountMap = new HashMap<>();
        if(!Utils.isWeekend(getAvailableFieldsRequest.getDate())){
            typeToAmountMap = priceList.stream()
                    .collect(Collectors.toMap(GetPriceResponse::getFieldType, GetPriceResponse::getAmount));
        } else{
            typeToAmountMap = priceList.stream()
                    .collect(Collectors.toMap(GetPriceResponse::getFieldType, GetPriceResponse::getSpecialAmount));
        }
        //create list
        List<GetAvailableFieldsResponse> availableFields = new ArrayList<GetAvailableFieldsResponse>();
        for(Field field : response.getFields()){
            Integer amount = typeToAmountMap.get(field.getType());
            availableFields.add((new GetAvailableFieldsResponse(field, amount)));
        }
        //remove booked ones
        for(Booking booking : placedBookings){
            String bookedFieldIndex = booking.getFieldIndex();
            availableFields.removeIf(element -> bookedFieldIndex.equals(element.getField().getIndex()));
        }
        return availableFields;
    }

    public Booking switchStatus(SwitchStatusRequest request ){
        Booking booking = bookingRepository.findById(request.getBookingId());
        if (booking == null) {
            throw new AppException(ErrorCommon.BOOKING_NOT_FOUND);
        }
        if(booking.getOpponentId()!=null){
            throw new BaseException("This booking has opponent, can not switch Status");
        }
        booking.setHasOpponent(!booking.isHasOpponent());
        booking = bookingRepository.save(booking);
//        kafkaTemplate.send("notification-topic", new CreateBookingEvent(bookingSaved.get_id().toString()));
        return booking;
    }

    public MatchingRequest matchingRequest(MatchingRestRequest request, AuthUserDetails authUserDetails){
        String requestorId = authUserDetails.getId();
        MatchingRequest matchingRequest = MatchingRequest.builder()
                .bookingId(request.getBookingId())
                .requestorId(requestorId)
                .status(MatchingRequestStatus.PENDING)
                .build();
        MatchingRequest matchingRequestSaved = matchingRequestRepository.save(matchingRequest);
        return matchingRequestSaved;
    }
}
