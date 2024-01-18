package nghhng.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import nghhng.common.enums.ERole;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
