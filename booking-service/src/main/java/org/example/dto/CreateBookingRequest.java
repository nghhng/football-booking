package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.example.dao.part.Field;

@Data
@Builder
@AllArgsConstructor
public class CreateBookingRequest {
    @JsonProperty(required = true)
    @NonNull
    private String username;

    @JsonProperty(required = true)
    @NonNull
    private ObjectId facilityId;

    private Field field;

    private ObjectId ownerId;

    @NonNull
    private String username;

    @NonNull
    @JsonProperty("fields")
    private Field[] fields;
}
