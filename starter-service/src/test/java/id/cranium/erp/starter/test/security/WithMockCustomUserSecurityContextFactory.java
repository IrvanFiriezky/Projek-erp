package id.cranium.erp.starter.test.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import id.cranium.erp.starter.dto.UserAuthInfoDto;
import id.cranium.erp.starter.entity.JwtUserDetails;
import id.cranium.erp.starter.security.JwtAuthenticationToken;
import id.cranium.erp.starter.test.security.annotation.WithMockCustomUser;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

        String[] permissions = customUser.authorities();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (permissions.length > 0) {
            authorities = Arrays.asList(permissions).stream().map(permission -> new SimpleGrantedAuthority(permission)).collect(Collectors.toList());
        }

        User user = new User(customUser.username(), RandomStringUtils.randomAlphanumeric(60), authorities);
        UserAuthInfoDto userAuthInfoDto = UserAuthInfoDto.builder()
                    .userId(customUser.userId())
                    .username(customUser.username())
                    .build();
        JwtUserDetails jwtUserDetails = new JwtUserDetails(user, userAuthInfoDto);
        
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(jwtUserDetails,
                null, authorities, null);
        context.setAuthentication(authToken);
        return context;
	}
    
}
