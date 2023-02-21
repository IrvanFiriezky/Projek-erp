package id.cranium.erp.auth.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import id.cranium.erp.auth.util.AdminAuthJwtUtil;
import id.cranium.erp.starter.dto.UserAuthInfoDto;
import id.cranium.erp.starter.entity.JwtUserDetails;
import id.cranium.erp.user.UserSpringBootTest;

public class AdminAuthJwtUtilTest extends UserSpringBootTest {
    
    @Autowired
    private AdminAuthJwtUtil adminAuthJwtUtil;

    @Test
    void generateToken() {
        Long userId = 12L;
        String username = "user-12";
        String password = "pass-12";
        List<GrantedAuthority> authorities = new ArrayList<>();

        User user = new User(username, password, authorities);

        UserAuthInfoDto userAuthInfoDto = UserAuthInfoDto.builder()
            .userId(userId)
            .username(username)
            .build();

        JwtUserDetails jwtUserDetails = new JwtUserDetails(user, userAuthInfoDto);

        String token = adminAuthJwtUtil.generateToken(jwtUserDetails);
        assertThat(token).isNotNull();
    }
}
