package id.cranium.erp.auth.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.cranium.erp.auth.service.LoginService;
import id.cranium.erp.starter.dto.ApiErrorDto;
import id.cranium.erp.user.dto.LoginDto;
import id.cranium.erp.user.enums.LoginStatus;

public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ResourceBundleMessageSource authMessageSource;
    private LoginService loginService;
	private final String AUTH_LOGIN_FAILED = "auth.login.failed";
    private final String USERNAME_PARAMETER = "username";
    private final Long NO_USER_ID = 0L;

    public JwtAuthenticationFailureHandler(ResourceBundleMessageSource authMessageSource, LoginService loginService) {
        this.authMessageSource = authMessageSource;
        this.loginService = loginService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException ex) throws IOException {
        String error = authMessageSource.getMessage(AUTH_LOGIN_FAILED, null, LocaleContextHolder.getLocale());
        ApiErrorDto apiErrorDto = new ApiErrorDto(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), error);

        LoginDto loginDto = new LoginDto();
        loginDto.setAccessToken("FAILED-" + UUID.randomUUID().toString());
        loginDto.setUsername(httpServletRequest.getParameter(USERNAME_PARAMETER));
        loginDto.setUserId(NO_USER_ID);
        loginDto.setStatus(LoginStatus.INVALID.isValue());

        loginDto = loginService.createLogin(loginDto);

        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(apiErrorDto);
        httpServletResponse.getOutputStream().println(jsonPayload);
    }
}
