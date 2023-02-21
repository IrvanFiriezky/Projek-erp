package id.cranium.erp.auth.util;

import io.jsonwebtoken.JwtException;
import java.util.HashMap;
import java.util.Map;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.starter.entity.JwtUserDetails;

@Slf4j
public abstract class AuthJwtBaseUtil {
    
    protected abstract String createToken(Map<String, Object> claims, String subject);

    public String generateToken(JwtUserDetails jwtUserDetails) {
        Map<String, Object> claims = new HashMap<>();
        log.info("TESTING: AdminAuthJwtUtils - generateToken: " + jwtUserDetails.getJwtAuthorities());
        claims.put("authorities",jwtUserDetails.getJwtAuthorities());
        return createToken(claims, jwtUserDetails.getUsername());
    }

    protected PrivateKey loadPrivateKey(String privateKeyProperty) {
        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyProperty);
            KeyFactory privateKeyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = privateKeyFactory.generatePrivate(privateKeySpec);
            return privateKey;
        } catch(NoSuchAlgorithmException ex) {
            throw new JwtException(ex.getMessage());
        } catch(InvalidKeySpecException ex) {
            throw new JwtException(ex.getMessage());
        }
    }
}
