package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dao.part.Time;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetBookingDetailResponse {
    private String id;

    private String userId;

    private String facilityId;

    private String fieldIndex;

    private String priceId;

    private Time startAt;

    private Time endAt;

    private String price;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private String date;

    private boolean hasOpponent;

    private String opponentId;

    @Field("user.name")
    private String userName;

    @Field("user.age")
    private int userAge;

    @Field("user.image")
    private String userImage;

    @Field("facility.name")
    private String facilityName;

}
