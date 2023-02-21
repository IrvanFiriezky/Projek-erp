package id.cranium.erp.starter.util;

import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.jsonwebtoken.ExpiredJwtException;
import id.cranium.erp.starter.StarterSpringBootTest;

public class UserJwtUtilTest extends StarterSpringBootTest {
    
    @Autowired
    private UserJwtUtil userJwtUtil;

    private String token = "eyJhbGciOiJSUzI1NiJ9.eyJhdXRob3JpdGllcyI6IlJPTEVfRVJQX0NSRUFURSxST0xFX0VSUF9ERUxFVEUsUk9MRV9FUlBfUkVBRCxST0xFX0VSUF9VUERBVEUiLCJzdWIiOiJ1c2VyLTEyIiwiaXNzIjoiZXJwLmNyYW5pdW0uaWQiLCJqdGkiOiI1MjFjNmM2NS05MTA5LTRhZDgtODE5YS03MDI5YTZkNmM5MWUiLCJpYXQiOjE2NzY4NzU0NDIsImV4cCI6MTY3NjkwMDQ3NX0.YheeJtpozwVE9k3NYgDAWFarHls63PGxnDM19GRglF8pv9bUlKExAJcclT6OFAINhMAzyASEJuqNhmJvHanmUXZV-Q_nFqd_pFVMLW3jfAF0rrJc2bgXEcYOMaaG4c-4j_0vdUZFYXnMusLkwU0wzZ_OB9NOuNW8q2bE58xnJHsvxJPVOJbr4h9qw5kufawwwiaexlG_ORNfRprdFYvLL8_7vNGrTxU_Ng2pbvjPYt0i5cHi-GJ7Ve5F8WFGa7rgLDXitnpQUwBhuXQIcMG15VlBfxec8HdNjEM5aUx9W0HDm2JWCtOA_yuqC9nYzv81U3dbxcOpzui8em4FtyWFVg";
    private String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJhdXRob3JpdGllcyI6IlJPTEVfRVJQX0NSRUFURSxST0xFX0VSUF9ERUxFVEUsUk9MRV9FUlBfUkVBRCxST0xFX0VSUF9VUERBVEUiLCJzdWIiOiJ1c2VyLTEyIiwiaXNzIjoiZXJwLmNyYW5pdW0uaWQiLCJqdGkiOiJjNGNhODg2Ni1lYTE5LTQwMmQtYTQxYS1mMmZjZGZkZTNkOTkiLCJpYXQiOjE2NzY4NzU0NDIsImV4cCI6MTY3Njc4OTA0Mn0.cXOk6ptx5U6mZjs71sDvKVOtmoNIYKn8vfksghoMt-2KB8YiUQhxiRt_9rfJYzZu6sP_Wqcg3ULkcK1DuFURhJryEpdm8Z1uBc-oFJYit1fT9TYY6On3paUVgkAHTXCg6fYea2OH2rlGoei_WtxCBpHx9MDf9_8P65SVBPzrVbU5gsc_x1hVQvRZhbYOGL4niPQRTjGkE66Y26fuRu-gDuiDSN_O_Bp8PODOMcyVNzC3XsawTOJs69_HGGJZrOkdBFnvEZBR0hdU4zoohRHfrNFjG4r3emYDtxulm3MYl7u5dLER-RpmQAz9UOqUTLQ8I0F78bW6XvA6hFT7Ss5snA";

    @Test
    void validateToken_returnTrue() {
        //Mockito.when(userJwtUtil.isTokenExpired(token)).thenReturn(false);
        boolean result = userJwtUtil.validateToken(token);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(true);
    }

    @Test
    void validateToken_returnException() {
        Exception exception = assertThrows(ExpiredJwtException.class, () -> {
            boolean result = userJwtUtil.validateToken(expiredToken);
        });
    
        String expectedMessage = "JWT expired";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void extractIssuer() {
        String result = userJwtUtil.extractIssuer(token);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("erp.cranium.id");
    }

    @Test
    void extractUsername() {
        String result = userJwtUtil.extractUsername(token);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("user-12");
    }

    @Test
    void extractAccessToken() {
        String result = userJwtUtil.extractAccessToken(token);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("521c6c65-9109-4ad8-819a-7029a6d6c91e");
    }

    @Test
    void extractAuthorities() {
        String result = userJwtUtil.extractAuthorities(token);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("ROLE_ERP_CREATE,ROLE_ERP_DELETE,ROLE_ERP_READ,ROLE_ERP_UPDATE");
    }
}
