package id.cranium.erp.starter.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import java.util.Date;
import java.util.function.Function;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public abstract class JwtBaseUtil {

    protected abstract Claims extractAllClaims(String token);
    
    public String extractIssuer(String token) {
        return extractClaim(token, Claims::getIssuer);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractAccessToken(String token) {
        return extractClaim(token, Claims::getId);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractAuthorities(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("authorities", String.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    protected Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    protected PublicKey loadPublicKey(String publicKeyProperty) {
        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyProperty);
            KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = publicKeyFactory.generatePublic(publicKeySpec);
            return publicKey;
        } catch(NoSuchAlgorithmException ex) {
            throw new JwtException(ex.getMessage());
        } catch(InvalidKeySpecException ex) {
            throw new JwtException(ex.getMessage());
        }
    }
}
