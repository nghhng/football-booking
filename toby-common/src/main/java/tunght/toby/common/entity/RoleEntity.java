package tunght.toby.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tunght.toby.common.enums.ERole;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "roles")
public class RoleEntity {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    @Enumerated(EnumType.STRING)
    private ERole role;
}
