package tunght.toby.common.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tunght.toby.common.entity.RoleEntity;
import tunght.toby.common.enums.ERole;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Schema(hidden = true)
public class AuthUserDetails implements UserDetails {
    @Schema(hidden = true)
    private final String id;
    @Schema(hidden = true)
    private final String email;
    @Schema(hidden = true)
    private final Set<ERole> authorities;

    @Builder
    public AuthUserDetails(String id, String email, Set<ERole> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var grantedAuthorities = new HashSet<GrantedAuthority>();
        for (ERole role: this.authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
