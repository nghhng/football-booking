package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.example.dao.part.Time;

@Data
@Builder
@AllArgsConstructor
public class CreateBookingRequest {
    @NonNull
    private String facilityId;

    @NonNull
    private String fieldIndex;


    @NonNull
    private String userId;

    @NonNull
    private Time startAt;

    @NonNull
    private Time endAt;

    @NonNull
    private String date;



}
