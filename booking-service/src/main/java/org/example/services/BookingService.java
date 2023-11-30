package org.example.services;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.example.access.*;
import org.example.access.model.*;
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
        GetFacilityByIdResponse bookedFacility = facilityFeignClient.getFacilityById(getFacilityByFacilityIdRequest);
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

        GetUserResponse user = userFeignClient.getUserById(new GetUserByIdRequest(booking.getUserId()));


        NotificationDto notificationDto = NotificationDto.builder()
                .type(ENotifications.BOOKING)
                .fromUserId(authUserDetails.getId())
                .toUserId(bookedFacility.getOwnerId())
                .bookingId(booking.getId())
                .message(ENotifications.getNotificationMessage(ENotifications.BOOKING, user.getUsername()))
                .isRead(false)
                .build();

        notiKafkaTemplate.send(bookingNotiTopic, JsonConverter.serializeObject(notificationDto));
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
                    }
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

    public List<Field> getAvailableFieldsByTimeAndDayAndFacility(GetAvailableFieldsRequest getAvailableFieldsRequest){
        GetBookingRequest getPlacedBookingRequest = GetBookingRequest.builder()
                .facilityId(getAvailableFieldsRequest.getFacilityId())
                .startAt(getAvailableFieldsRequest.getStartAt())
                .endAt(getAvailableFieldsRequest.getEndAt())
                .date(getAvailableFieldsRequest.getDate())
                .build();
        List<Booking> placedBookings = this.getBooking(getPlacedBookingRequest);

        //check Facility and maximum number of fields
        GetFacilityByFacilityIdRequest getFacilityByFacilityIdRequest = new GetFacilityByFacilityIdRequest(getAvailableFieldsRequest.getFacilityId());

        GetFacilityByIdResponse response = facilityFeignClient.getFacilityById(getFacilityByFacilityIdRequest);
        if(response==null){
            throw new BaseException("Facility not exists");
        }
        List<Field> availableFields = new ArrayList<Field>();
        for(Field field : response.getFields()){
            availableFields.add(field);
        }
        //calculate available field
        for(Booking booking : placedBookings){
            String bookedFieldIndex = booking.getFieldIndex();
            availableFields.removeIf(element -> bookedFieldIndex.equals(element.getIndex()));
        }
        return availableFields;
    }

    public Booking switchStatus(SwitchStatusRequest request ){
        Booking booking = bookingRepository.findById(request.getBookingId());
        if (booking == null) {
            throw new AppException(ErrorCommon.BOOKING_NOT_FOUND);
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
