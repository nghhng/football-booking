package nghhng.facilityservice.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "facility")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Facility {

    @Id
    private ObjectId id;

    private String name;

    private Address address;

    private String numOfFields;

    private ObjectId ownerId;


}


