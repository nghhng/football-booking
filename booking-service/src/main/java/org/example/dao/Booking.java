package org.example.dao;

import org.bson.types.ObjectId;
import org.example.dao.part.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Booking {


    private ObjectId _id;

    private ObjectId userId;

    private ObjectId facilityId;

    private Field field;

    private ObjectId priceId;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private String date;

    private String hasOpponent;

}

