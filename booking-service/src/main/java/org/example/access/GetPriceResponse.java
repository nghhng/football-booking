package org.example.access;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPriceResponse {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;

    private ObjectId facilityId;

    private String fieldType;

    private Time startAt;

    private Time endAt;

    private int amount;

    private int specialAmount;
}
