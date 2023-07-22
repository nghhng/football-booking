package org.example.access;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPriceRequest {
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonProperty("facilityId")
    private ObjectId facilityId;

    private String fieldType;

    private Time startAt;

    private Time endAt;

    private int amount;

    private int specialAmount;

    public GetPriceRequest(ObjectId facilityId, String fieldType, Time startAt, Time endAt) {
        this.facilityId = facilityId;
        this.fieldType = fieldType;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
