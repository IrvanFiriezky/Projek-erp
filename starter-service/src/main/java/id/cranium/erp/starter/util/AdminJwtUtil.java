package id.cranium.erp.starter.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//import io.jsonwebtoken.security.Keys;
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
import java.security.PublicKey;

@Component
public class AdminJwtUtil extends JwtBaseUtil {
    
    @Value("${starter.security.jwt.token.issuer}")
    protected String issuer;

    @Value("${starter.security.jwt.token.publicKeyAdmin}")
    protected String publicKeyProperty;

    protected Claims extractAllClaims(String token) {
        //SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        PublicKey key = loadPublicKey(publicKeyProperty);
        return Jwts.parserBuilder().requireIssuer(issuer).setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    
}
