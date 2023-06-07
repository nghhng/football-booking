package nghhng.facilityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nghhng.facilityservice.dao.Address;
import org.bson.types.ObjectId;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateFacilityRequest {

    private String name;

    private Address address;

    private String numOfFields;

    private ObjectId ownerId;

    private String username;
}
