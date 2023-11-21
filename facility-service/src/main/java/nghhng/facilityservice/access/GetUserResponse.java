package nghhng.facilityservice.access;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nghhng.facilityservice.middlewares.ObjectIdDeserializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserResponse {


    @JsonProperty("id")
    private String id;

    private String name;

    private String age;

    private String gender;

    private String phone;

    private String email;

    private String username;

    private String password;

    private String birthDate;
}
