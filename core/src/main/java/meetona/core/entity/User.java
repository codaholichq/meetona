package meetona.core.entity;

import meetona.core.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Transient;
import meetona.core.common.BaseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity implements UserDetails {

    private String firstname;
    private String lastname;
    protected String username;
    private String email;
    protected String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    protected Set<Role> roles = new HashSet<>();

    @Column(name = "unit_id")
    private long unitId;

    @Transient
    private Units units;

    private Boolean isEmailVerified;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() { return username;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { return true; }

}