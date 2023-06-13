package org.example.services;


import org.example.access.FacilityFeignClient;
import org.example.access.Field;
import org.example.access.GetFacilityByFacilityIdRequest;
import org.example.access.GetFacilityByIdResponse;
import org.example.dao.Booking;
import org.example.dto.CreateBookingRequest;
import org.example.exception.BaseException;
import org.example.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    FacilityFeignClient facilityFeignClient;


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


        Booking booking = Booking.builder()
                .facilityId(createBookingRequest.getFacilityId())
                .userId(createBookingRequest.getUserId())
                .date(createBookingRequest.getDate())
                .
    }
}
