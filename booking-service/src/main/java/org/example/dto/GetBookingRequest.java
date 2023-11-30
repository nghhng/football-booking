package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.example.dao.part.Time;

@Data
@Builder
@AllArgsConstructor
public class GetBookingRequest {

    private String facilityId;

    private String fieldIndex;

    private String userId;
    private Time startAt;
    private Time endAt;

    private String date;
    private String hasOpponent;

    private Integer limit;
    private Integer skip;
}
