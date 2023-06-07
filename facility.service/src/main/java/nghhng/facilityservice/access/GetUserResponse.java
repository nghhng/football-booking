package nghhng.facilityservice.access;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class GetUserResponse {
    private ObjectId id;

    private String name;

    private String age;

    private String gender;

    private String phone;

    private String email;

    private String username;

    private String password;

    private String birthDate;
}
