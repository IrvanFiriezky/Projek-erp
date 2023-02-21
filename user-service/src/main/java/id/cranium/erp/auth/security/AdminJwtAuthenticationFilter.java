package id.cranium.erp.auth.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import id.cranium.erp.starter.security.JwtAuthenticationToken;
import id.cranium.erp.starter.entity.JwtUserDetails;
import id.cranium.erp.starter.dto.UserAuthInfoDto;
import id.cranium.erp.starter.util.AdminJwtUtil;
import id.cranium.erp.auth.util.AdminAuthJwtUtil;
import id.cranium.erp.auth.service.LoginService;
import id.cranium.erp.user.dto.LoginDto;
import id.cranium.erp.user.enums.LoginStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminJwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private AdminJwtUtil adminJwtUtil;
    private AdminAuthJwtUtil adminAuthJwtUtil;
    private LoginService loginService;

    public AdminJwtAuthenticationFilter(AdminJwtUtil adminJwtUtil, AdminAuthJwtUtil adminAuthJwtUtil, LoginService loginService) {
        this.adminJwtUtil = adminJwtUtil;
        this.adminAuthJwtUtil = adminAuthJwtUtil;
        this.loginService = loginService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
        throws AuthenticationException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        JwtAuthenticationToken authRequest = getAuthRequest(request);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private JwtAuthenticationToken getAuthRequest(HttpServletRequest request) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        log.info("TESTING: AdminJwtAuthenticationFilter - getAuthRequest " + username + " - " + password);

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }

        return new JwtAuthenticationToken(username, password, null);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        JwtUserDetails jwtUserDetails = (JwtUserDetails) auth.getPrincipal();
        String token = this.adminAuthJwtUtil.generateToken(jwtUserDetails);

        UserAuthInfoDto userAuthInfoDto = (UserAuthInfoDto) jwtUserDetails.getInfo();

        LoginDto loginDto = new LoginDto();
        loginDto.setAccessToken(adminJwtUtil.extractAccessToken(token));
        loginDto.setUsername(adminJwtUtil.extractUsername(token));
        loginDto.setUserId(userAuthInfoDto.getUserId());
        loginDto.setStatus(LoginStatus.VALID.isValue());

        loginDto = loginService.createLogin(loginDto);

        String body = ((JwtUserDetails) auth.getPrincipal()).getUsername() + " " + token;

        res.getWriter().write(body);
        res.getWriter().flush();
    }

}
