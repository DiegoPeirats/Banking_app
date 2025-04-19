package com.diego_peirats.infrastructure.client.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import user.Role;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsDto implements UserDetails {

    private static final long serialVersionUID = -4357693878213793944L;
    private String email;
    private String password;
    private Role role;
    private boolean enabled;

    @Override
    @JsonProperty("authorities")
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	if (this.role == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(List.of(new SimpleGrantedAuthority(role.name())));
    }

    @Override
    @JsonProperty(value = "authorities", access = JsonProperty.Access.READ_ONLY)
    public String getUsername() {
        return email;
    }

    @JsonCreator
    public static UserDetailsDto createFromJson(@JsonProperty("username") String email,
                                                @JsonProperty("password") String password,
                                                @JsonProperty("role") String role,
                                                @JsonProperty("enabled") boolean enabled) {
        Role userRole = Role.valueOf(role);
        return new UserDetailsDto(email, password, userRole, enabled);
    }
}
