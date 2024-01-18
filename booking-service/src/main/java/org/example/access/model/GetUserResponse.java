package org.example.access.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nghhng.common.entity.BaseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserResponse extends BaseEntity {


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
