package org.example.dao;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import nghhng.common.entity.BaseEntity;
import nghhng.common.enums.ERole;
import nghhng.common.enums.EStatus;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Set;

@Document(collection = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class User extends BaseEntity {

    @Id
    private String id;

    private String name;

    private String age;

    private String gender;

    private String phone;

    private String email;

    private String username;

    private String password;

    private String image;

    private String otp;

    @Enumerated(EnumType.STRING)
    private Set<ERole> roles;


    @Enumerated(EnumType.STRING)
    private EStatus status;

    private String trackingId;

    private String merchantId;
}


