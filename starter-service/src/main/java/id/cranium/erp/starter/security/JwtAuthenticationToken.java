package id.cranium.erp.starter.security;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    
    private Object data;

    public JwtAuthenticationToken(Object principal, Object credentials, Object data) {
        super(principal, credentials);
        this.data = data;
    }

    public JwtAuthenticationToken(Object principal, Object credentials, 
        Collection<? extends GrantedAuthority> authorities, Object data) {
        super(principal, credentials, authorities);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
