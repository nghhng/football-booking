package org.example.access.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.example.dao.part.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPriceRequest {

    private String facilityId;

    private String fieldType;

    private Time startAt;

    private Time endAt;

    private int amount;

    private int specialAmount;

    public GetPriceRequest(String facilityId, String fieldType, Time startAt, Time endAt) {
        this.facilityId = facilityId;
        this.fieldType = fieldType;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
