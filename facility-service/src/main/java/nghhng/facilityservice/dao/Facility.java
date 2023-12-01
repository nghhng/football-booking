package nghhng.facilityservice.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nghhng.facilityservice.dao.part.Address;
import nghhng.facilityservice.dao.part.Field;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tunght.toby.common.entity.BaseEntity;
import tunght.toby.common.entity.Comment;

import java.util.List;

@Document(collection = "facility")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Facility {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(value = "address", required = true)

    private Address address;

    @JsonProperty(required = true)
    private String numOfFields;

    private String ownerId;

    private List<Field> fields;

    private List<Comment> comments;

    private Double rating;
}


