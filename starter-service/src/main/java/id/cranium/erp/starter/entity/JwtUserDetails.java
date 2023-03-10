package id.cranium.erp.starter.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

public class JwtUserDetails implements UserDetails {
    
    private final User user;
    private final Object info;

    public JwtUserDetails(User user, Object info) {
        this.user = user;
        this.info = info;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    public Object getInfo() {
        return info;
    }
    
    public String getJwtAuthorities() {
        return getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.joining(","));
    }

}
