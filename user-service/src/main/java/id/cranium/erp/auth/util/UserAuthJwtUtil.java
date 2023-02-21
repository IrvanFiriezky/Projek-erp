package id.cranium.erp.auth.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Map;
//import io.jsonwebtoken.security.Keys;
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.UUID;

@Component
public class UserAuthJwtUtil extends AuthJwtBaseUtil {

    private final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;

    @Value("${auth.security.jwt.token.issuer}")
    private String issuer;

    @Value("${auth.security.jwt.token.privateKey}")
    private String privateKeyProperty;

    protected String createToken(Map<String, Object> claims, String subject) {
        //SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        PrivateKey key = loadPrivateKey(privateKeyProperty);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuer(issuer)
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * JWT_TOKEN_VALIDITY))
            .signWith(key)
            .compact();
    }

}
