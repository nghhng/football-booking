package tunght.toby.common.entity;

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
import tunght.toby.common.enums.EStatus;

import javax.persistence.*;
import java.util.Set;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String name;

    private String age;

    private String gender;

    private String phone;

    private String email;

    @Indexed(unique=true)
    private String username;

    private String password;

    private String image;

    private String otp;


    @Enumerated(EnumType.STRING)
    private EStatus status;


}


