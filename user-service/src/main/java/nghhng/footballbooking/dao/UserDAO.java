package nghhng.footballbooking.dao;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDAO {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId _id;

    private String name;

    private String age;

    private String gender;

    private String phone;

    private String email;

    @Indexed(unique=true)
    private String username;

    private String password;

    private String birthDate;
}


