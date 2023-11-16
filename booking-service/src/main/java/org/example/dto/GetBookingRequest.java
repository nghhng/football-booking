package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.example.access.Time;
import org.example.dao.part.BookField;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GetBookingRequest {

    private ObjectId facilityId;

    private String fieldIndex;

    private ObjectId userId;
    private Time startAt;
    private Time endAt;

    private String date;
    private String hasOpponent;
}
