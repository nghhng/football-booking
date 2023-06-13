package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.example.dao.part.Field;

import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
public class CreateBookingRequest {
    @NonNull
    private ObjectId facilityId;

    @NonNull
    private String fieldType;

    @NonNull
    private int numberOfFields;

    @NonNull
    private ObjectId userId;

    @NonNull
    private Time startAt;

    @NonNull
    private Time endAt;

    @NonNull
    private String date;

    @NonNull
    private String hasOpponent;


}
