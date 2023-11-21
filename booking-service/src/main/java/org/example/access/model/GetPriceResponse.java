package org.example.access.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.example.dao.part.Time;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPriceResponse {

    private String id;

    private String facilityId;

    private String fieldType;

    private Time startAt;

    private Time endAt;

    private int amount;

    private int specialAmount;
}
