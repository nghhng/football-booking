package tunght.toby.auth.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nghhng.common.enums.ERole;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
@JsonTypeName("user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class UserDto {
    private String id;
    private String email;
    private String username;
    private String name;
//    private String bio;
    private String image;
    @Enumerated(EnumType.STRING)
    private Set<ERole> roles;
    private String phone;
    private String age;
    private String gender;
    private String trackingId;
    private String merchantId;


    @Getter
    @AllArgsConstructor
    @Builder
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Registration {
        @NotBlank(message = "Username không được để trống")
        @Pattern(regexp = "[\\w\\d]{1,30}", message = "Username chỉ được bao gồm chữ cái, kí tự số hoặc gạch dưới, ít nhất 1 kí tự số")
        private String username;

        @NotBlank(message = "Tên không được để trống")
        private String name;

        @NotBlank(message = "Email không được để trống")
        @Email(message = "Email không đúng định dạng")
        private String email;

        @NotBlank(message = "Mật khẩu không được để trống")
        @Size(min = 8, max = 32, message = "Mật khẩu phải từ 8-32 kí tự")
        private String password;

        @NotBlank(message = "Role không được để trống")
        @Size(min = 8, max = 32, message = "ROLE_USER hoặc ROLE_OWNER")
        private String role;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class RegistrationResponse {
        private String email;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Login {
        @NotBlank(message = "Email không được để trống")
        @Email(message = "Email không đúng định dạng")
        private String email;

        @NotBlank(message = "Mật khẩu không được để trống")
        @Size(min = 8, max = 32, message = "Mật khẩu phải từ 8-32 kí tự")
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @Builder
//    @JsonTypeName("user")
//    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Update {
        private String name;
        private String email;
        private String username;
        private String phone;
        private String age;
        private String gender;
        private String image;
        private String trackingId;
        private String merchantId;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class RequestOTP {
        private String email;
        private String otp;
    }
}
