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
import tunght.toby.common.entity.BaseEntity;
import tunght.toby.common.entity.Comment;
import tunght.toby.common.enums.ERole;
import tunght.toby.common.enums.EStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.Set;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDAO {

    @Id
    private String id;

    private String name;

    private String age;

    private String gender;

    private String phone;

    private String email;

    private String username;

    private String password;

    private String birthDate;

    private String image;

    private List<Comment> comments;

    private Double rating;

    @Enumerated(EnumType.STRING)
    private Set<ERole> roles;


    @Enumerated(EnumType.STRING)
    private EStatus status;
}


