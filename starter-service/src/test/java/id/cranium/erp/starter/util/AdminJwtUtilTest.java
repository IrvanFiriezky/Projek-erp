package id.cranium.erp.starter.util;

import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.jsonwebtoken.ExpiredJwtException;
import id.cranium.erp.starter.StarterSpringBootTest;

public class AdminJwtUtilTest extends StarterSpringBootTest {
    
    @Autowired
    private AdminJwtUtil adminJwtUtil;

    private String token = "eyJhbGciOiJSUzI1NiJ9.eyJhdXRob3JpdGllcyI6IlJPTEVfRVJQX0FETUlOLFJPTEVfRVJQX0NSRUFURSxST0xFX0VSUF9ERUxFVEUsUk9MRV9FUlBfUkVBRCxST0xFX0VSUF9VUERBVEUiLCJzdWIiOiJhZG1pbi0xMSIsImlzcyI6ImVycC5jcmFuaXVtLmlkIiwianRpIjoiNTAxMTcwMjMtYzFhYy00ODI3LTljNTgtMjEzOTk2NjQyNzA1IiwiaWF0IjoxNjc2ODc3MTU2LCJleHAiOjE2NzY5MDIxODh9.K9xOdqM4eWxemKO6RiXCPRQsT9kCpUDqgdPW24-Tl_5wP3PG0MNLe-E5aGnNWWNFjtY7m92K0qo6vsnx8vTnYyhZ4u15e2sDwjj_lDn4e-A5dJbbHXoKC4CyP8OxJrH-BNRspIAyXv2tSLMwe6EWnSsVOWVD0914xD0CYa4TodobQHgZrz2QK7CJOTELArMFyBjE0cdQay_fejUhMLLiA4DCFCQOdx97KjJGk_mS_sXhJBPJoJpMcXDaRqU5uyJmjpFtUcv_DDY0hRB-lX3hGSxTmh4GEOewvTxC4fX0oTKgXCNHjsdcfr2o5LHxUQT3bY_-JSQzPQGlzwI3f74s4g";
    private String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJhdXRob3JpdGllcyI6IlJPTEVfRVJQX0FETUlOLFJPTEVfRVJQX0NSRUFURSxST0xFX0VSUF9ERUxFVEUsUk9MRV9FUlBfUkVBRCxST0xFX0VSUF9VUERBVEUiLCJzdWIiOiJhZG1pbi0xMSIsImlzcyI6ImVycC5jcmFuaXVtLmlkIiwianRpIjoiMWM3ZTQyOGYtOGM4MC00MDI3LTg4NWEtYzc4N2ViYTFjNWRhIiwiaWF0IjoxNjc2ODc3MTU2LCJleHAiOjE2NzY3OTA3NTZ9.bKHnLzkar1eLojIbTHuff8w9DxfbpIGvCn_F8xiR_F81zsLBOVaJ3b7QINE08jfATS8v_6c_hUwL8wghsjimKaN55M5j6U1h2IiJO1J1ffcFcENdt31iysSR2g4AJ-G7-CqMct8IsfnM_jqM9c1Xj2MVP92CavvE0eRle4A5MNlD9OuerVpcIyS0ylGivZkH2nBJcL7_5VweRpTju7nW98NkqikvbgJtZ7QsdUu01znflUE0dNkDuTPGbBR6nXnHZQz_cwFnLnWcVKzP9uQknt6JSJOvUBnGdWeSe201o9Lcu2suX2pNapPHhuaryVnc7Ao-jx2VTQH19aTw23raWA";

//    @Test
//    void validateToken_returnTrue() {
//        //Mockito.when(userJwtUtil.isTokenExpired(token)).thenReturn(false);
//        boolean result = adminJwtUtil.validateToken(token);
//        assertThat(result).isNotNull();
//        assertThat(result).isEqualTo(true);
//    }
//
//    @Test
//    void validateToken_returnExpired() {
//        Exception exception = assertThrows(ExpiredJwtException.class, () -> {
//            boolean result = adminJwtUtil.validateToken(expiredToken);
//        });
//
//        String expectedMessage = "JWT expired";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    void extractIssuer() {
//        String result = adminJwtUtil.extractIssuer(token);
//        assertThat(result).isNotNull();
//        assertThat(result).isEqualTo("erp.cranium.id");
//    }
//
//    @Test
//    void extractUsername() {
//        String result = adminJwtUtil.extractUsername(token);
//        assertThat(result).isNotNull();
//        assertThat(result).isEqualTo("admin-11");
//    }
//
//    @Test
//    void extractAccessToken() {
//        String result = adminJwtUtil.extractAccessToken(token);
//        assertThat(result).isNotNull();
//        assertThat(result).isEqualTo("50117023-c1ac-4827-9c58-213996642705");
//    }
//
//    @Test
//    void extractAuthorities() {
//        String result = adminJwtUtil.extractAuthorities(token);
//        assertThat(result).isNotNull();
//        assertThat(result).isEqualTo("ROLE_ERP_ADMIN,ROLE_ERP_CREATE,ROLE_ERP_DELETE,ROLE_ERP_READ,ROLE_ERP_UPDATE");
//    }
}
