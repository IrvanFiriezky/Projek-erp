package id.cranium.erp.starter.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import id.cranium.erp.starter.dto.UserAuthInfoDto;
import id.cranium.erp.starter.event.BaseEvent;
import id.cranium.erp.starter.entity.JwtUserDetails;

public class UserAuthInfo {

    public static final String CRON_USERNAME = "scheduler-09";
    public static final String SYSTEM_USERNAME = "system-01";
    public static final Long SYSTEM_USER_ID = 1L;
    public static final Long NOAUTH_USER_ID = 10L;
    public static Optional<UserAuthInfoDto> userAuthInfoDto = Optional.ofNullable(null);
    public static Optional<Map<String, Boolean>> authorities = Optional.ofNullable(null);
    
    public static UserAuthInfoDto getUserAuthInfo() {
        if (userAuthInfoDto.isPresent()) {
            return userAuthInfoDto.get();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            userAuthInfoDto = Optional.of(UserAuthInfoDto.builder()
                .userId(NOAUTH_USER_ID)
                .build());
            return userAuthInfoDto.get();
        }

        userAuthInfoDto = Optional.of((UserAuthInfoDto) ((JwtUserDetails) authentication.getPrincipal()).getInfo());

        return userAuthInfoDto.get();
    }

    public static Long getUserId() {
        return getUserAuthInfo().getUserId();
    }

    public static String getUsername() {
        return getUserAuthInfo().getUsername();
    }

    public static Map<String, Boolean> getAuthorities() {
        if (authorities.isPresent()) {
            return authorities.get();
        }
        else {
            authorities = Optional.of(new HashMap<String, Boolean>());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return authorities.get();
        }
        ((JwtUserDetails) authentication.getPrincipal()).getAuthorities().stream().map(authority -> authorities.get().put(authority.getAuthority(), true));

        return authorities.get();
    }

    public static boolean hasAuthority(String authority) {
        return getAuthorities().containsKey(authority);
    }

    public static void resetAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public static void setSystemUserAuthInfo() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        User user = new User(SYSTEM_USERNAME, RandomStringUtils.randomAlphanumeric(60), authorities);

        UserAuthInfoDto userAuthInfoDto = UserAuthInfoDto.builder()
                .userId(SYSTEM_USER_ID)
                .username(SYSTEM_USERNAME)
                .build();
        
        JwtUserDetails jwtUserDetails = new JwtUserDetails(user, userAuthInfoDto);
    
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(jwtUserDetails,
                null, authorities, null);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    public static void setCronUserAuthInfo(BaseEvent baseEvent) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        User user = new User(CRON_USERNAME, RandomStringUtils.randomAlphanumeric(60), authorities);

        UserAuthInfoDto userAuthInfoDto = UserAuthInfoDto.builder()
                .userId(baseEvent.getXUserId())
                .username(CRON_USERNAME)
                .build();
        
        JwtUserDetails jwtUserDetails = new JwtUserDetails(user, userAuthInfoDto);
    
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(jwtUserDetails,
                null, authorities, null);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
