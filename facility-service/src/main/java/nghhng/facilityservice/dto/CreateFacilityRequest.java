package nghhng.facilityservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import nghhng.facilityservice.dao.part.Address;
import nghhng.facilityservice.dao.part.Field;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CreateFacilityRequest {

    @JsonProperty(required = true)
    @NonNull
    private String name;

    @JsonProperty(required = true)
    @NonNull
    private Address address;

    private String numOfFields;

    private ObjectId ownerId;

    @NonNull
    private String username;

    @NonNull
    @JsonProperty("fields")
    private List<Field> fields;
}
