package org.example.access.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.example.dao.part.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "facility")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetFacilityByIdResponse {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private String numOfFields;

    private ObjectId ownerId;

    private List<Field> fields;
}


