package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.example.dao.part.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Booking {


    private ObjectId _id;

    private ObjectId userId;

    private ObjectId facilityId;

    private Field[] fields;

    private ObjectId priceId;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private String date;

    private String hasOpponent;

}

