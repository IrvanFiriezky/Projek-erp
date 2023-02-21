package id.cranium.erp.user.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import java.time.LocalDateTime;
import java.time.Month;
import id.cranium.erp.auth.controller.AuthController;
import id.cranium.erp.auth.service.LoginService;
import id.cranium.erp.user.dto.LoginDto;
import id.cranium.erp.starter.exception.RestResponseEntityExceptionHandler;
import id.cranium.erp.user.UserSpringBootTest;

public class UserAuthBase extends UserSpringBootTest {
    
    @MockBean
    private LoginService loginService;
    
    @Autowired
    private AuthController authController;

    @Autowired
    private ResourceBundleMessageSource starterMessageSource;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(authController)
            .setControllerAdvice(new RestResponseEntityExceptionHandler(starterMessageSource))
            .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain));
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        verifyToken_should_be_success();
    }

    private void verifyToken_should_be_success() {
        String accessToken = "11111111-2222-3333-4444-555555555555";

        LoginDto loginDto = new LoginDto();
        loginDto.setUserId(12L);
        loginDto.setUsername("user-test");
        loginDto.setAccessToken(accessToken);
        loginDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        loginDto.setCreatedBy(10L);
        loginDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        loginDto.setUpdatedBy(10L);
        loginDto.setDeleted(false);

        Mockito.when(loginService.findByAccessTokenStatusDeleted(accessToken)).thenReturn(loginDto);
    }
    
}
