package forex.trainer.ac.za.model.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import forex.trainer.ac.za.model.base.AbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@Table(name = "useraccount")
public class UserAccount extends AbstractPersistenceEntity implements UserDetails
{
    @Column(unique=true, nullable=false)
    private String username;


    @Column(unique=true, nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false, unique=true)
    private String mobile;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_permissions", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "permission", nullable = false)
    private List<Permission> permissions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus accountStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList());
        authorities.addAll(permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList());
        return authorities;
    }
    @Override
    public boolean isAccountNonExpired() {
        return !accountStatus.equals(AccountStatus.DEACTIVATED); // Account is expired if status is DEACTIVATED
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountStatus.equals(AccountStatus.ACTIVE); // Account is locked if not ACTIVE
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return accountStatus.equals(AccountStatus.ACTIVE); // Credentials are valid if account status is ACTIVE
    }

    @Override
    public boolean isEnabled() {
        return accountStatus.equals(AccountStatus.ACTIVE); // Active accounts are enabled
    }

}
