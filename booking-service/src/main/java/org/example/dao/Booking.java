package org.example.dao;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.example.dao.part.Time;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;


@Document(collection = "booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class Booking {

    @Id
    private String id;

    private String userId;

    private String userName;

    private String userImage;

    private String facilityId;

    private String facilityName;

    private String fieldIndex;

    private String priceId;

    private Time startAt;

    private Time endAt;

    private String price;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private String date;

    private boolean hasOpponent;

    private String opponentId;

    private String paypalOrderId;
}

