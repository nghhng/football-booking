package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.example.access.Time;
import org.example.dao.part.BookField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Booking {


    private ObjectId _id;

    private ObjectId userId;

    private ObjectId facilityId;

    private String fieldIndex;

    private ObjectId priceId;

    private Time startAt;

    private Time endAt;

    private String price;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private String date;

    private String hasOpponent;

}

