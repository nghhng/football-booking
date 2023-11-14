package tunght.toby.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ERole {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    ROLE_OWNER("ROLE_OWNER");

    private String value;

    public static boolean isRoleUser(String role){
        return ROLE_USER.getValue().contentEquals(role);
    }

    public static boolean isRoleOwner(String role){
        return ROLE_OWNER.getValue().contentEquals(role);
    }

}
