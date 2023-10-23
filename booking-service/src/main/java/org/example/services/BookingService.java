package org.example.services;


import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.example.access.*;
import org.example.dao.Booking;
import org.example.dao.part.BookField;
import org.example.dto.CreateBookingRequest;
import org.example.dto.GetAvailableFieldsRequest;
import org.example.dto.GetBookingRequest;
import org.example.event.CreateBookingEvent;
import org.example.exception.BaseException;
import org.example.repositories.BookingRepository;
import org.example.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    FacilityFeignClient facilityFeignClient;

    @Autowired
    MongoTemplate mongoTemplate;

//    private final KafkaTemplate<String, CreateBookingEvent> kafkaTemplate;




    public Booking createBooking(CreateBookingRequest createBookingRequest){

        GetFacilityByFacilityIdRequest getFacilityByFacilityIdRequest = new GetFacilityByFacilityIdRequest(createBookingRequest.getFacilityId());
        //check Facility and maximum number of fields
        GetFacilityByIdResponse response = facilityFeignClient.getFacilityById(getFacilityByFacilityIdRequest);
        if(response==null){
            throw new BaseException("Facility not exists");
        }
        for(Field field : response.getFields()){
            if(field.getType().equals(createBookingRequest.getFieldType())){
                if(Integer.parseInt(field.getNumber())<createBookingRequest.getNumberOfFields()){
                    throw new BaseException("Not enough fields to book");
                } else break;
            }
        }
        //get Price
        GetPriceRequest getPriceRequest = new GetPriceRequest(createBookingRequest.getFacilityId(), createBookingRequest.getFieldType(), createBookingRequest.getStartAt(), createBookingRequest.getEndAt());
        List<GetPriceResponse> getPriceResponses = facilityFeignClient.getPrice(getPriceRequest);

        String price = new String();
        //check the date is weekend of not then calculate price
        if(!Utils.isWeekend(createBookingRequest.getDate())){
            price = String.valueOf(getPriceResponses.get(0).getAmount()*createBookingRequest.getNumberOfFields());
        } else{
            price = String.valueOf(getPriceResponses.get(0).getSpecialAmount()*createBookingRequest.getNumberOfFields());
        }


        ObjectId priceId = getPriceResponses.get(0).get_id();
        //select Field
        //now set Field index random
        List<BookField> bookFields = new ArrayList<BookField>();
        for(int i=0; i<createBookingRequest.getNumberOfFields(); i++){
            BookField bookField = new BookField();
            bookField.setIndex(String.valueOf(i));
            bookField.setType(createBookingRequest.getFieldType());
            bookFields.add(bookField);
        }

        Booking booking = Booking.builder()
                .facilityId(createBookingRequest.getFacilityId())
                .userId(createBookingRequest.getUserId())
                .date(createBookingRequest.getDate())
                .priceId(priceId)
                .price(price)
                .startAt(createBookingRequest.getStartAt())
                .endAt(createBookingRequest.getEndAt())
                .hasOpponent(createBookingRequest.getHasOpponent())
                .bookFields(bookFields)
                .build();

        Booking bookingSaved = bookingRepository.save(booking);
//        kafkaTemplate.send("notification-topic", new CreateBookingEvent(bookingSaved.get_id().toString()));
        return  bookingSaved;
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

    public Map<String, String> getAvailableFieldsByTimeAndDayAndFacility(GetAvailableFieldsRequest getAvailableFieldsRequest){
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
        Map<String, String> availableFields = new HashMap<>();
        for(Field field : response.getFields()){
            availableFields.put(field.getType(), field.getNumber());
        }
        //calculate available field
        for(Booking booking : placedBookings){
            for(BookField bookField : booking.getBookFields()){
                String newNumber = String.valueOf(Integer.parseInt(availableFields.get(bookField.getType()))-1);
                availableFields.put(bookField.getType(),newNumber);
            }
        }
        return availableFields;
    }
}
