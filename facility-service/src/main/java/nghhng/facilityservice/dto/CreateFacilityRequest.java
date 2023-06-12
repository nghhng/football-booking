package nghhng.facilityservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import nghhng.facilityservice.dao.part.Address;
import org.bson.types.ObjectId;

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

    @JsonProperty(required = true)
    @NonNull
    private String numOfFields;

    private ObjectId ownerId;

    private String username;
}
