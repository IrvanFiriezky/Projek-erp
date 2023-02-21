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
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import id.cranium.erp.user.controller.AuthorityController;
import id.cranium.erp.user.service.AuthorityService;
import id.cranium.erp.user.dto.AuthorityDto;
import id.cranium.erp.user.dto.AuthorityCreateDto;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.exception.RestResponseEntityExceptionHandler;
import id.cranium.erp.starter.test.security.annotation.WithMockCustomUser;
import id.cranium.erp.user.UserSpringBootTest;

//@WithMockUser(username = "user-01", authorities = {"DOMAIN_MENU_PERMISSION","USER_AUTHORITY_READ"})
@WithMockCustomUser(userId = 12L, username = "user-12", authorities = {"DOMAIN_MENU_PERMISSION","USER_AUTHORITY_READ"})
public class UserAuthorityBase extends UserSpringBootTest {
    
    @MockBean
    private AuthorityService authorityService;

    @Autowired
    private AuthorityController authorityController;

    @Autowired
    private ResourceBundleMessageSource starterMessageSource;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(authorityController)
            .setControllerAdvice(new RestResponseEntityExceptionHandler(starterMessageSource))
            .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain));
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        getAuthorityById_should_be_success();
        getAuthorityById_should_be_failed();
        createAuthority_should_be_success();
    }
    
    private void getAuthorityById_should_be_success() {
        AuthorityDto authorityDto = new AuthorityDto();
        authorityDto.setId(1L);
        authorityDto.setAuthorityName("DOMAIN_AUTHORITY_TEST");
        authorityDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        authorityDto.setCreatedBy(1L);
        authorityDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        authorityDto.setUpdatedBy(1L);
        authorityDto.setDeleted(false);
        Mockito.when(authorityService.findById(1L)).thenReturn(authorityDto);
    }

    private void getAuthorityById_should_be_failed() {
        Mockito.when(authorityService.findById(20L)).thenThrow(new DataNotFoundException("Authority dengan id 20 tidak ada"));
    }

    private void createAuthority_should_be_success() {
        String authorityName = "DOMAIN_AUTHORITY_NEW";

        AuthorityCreateDto authorityCreateDto = AuthorityCreateDto.builder()
            .authorityName(authorityName)
            .build();

        AuthorityDto authorityDto = new AuthorityDto();
        authorityDto.setId(2L);
        authorityDto.setAuthorityName(authorityName);
        authorityDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        authorityDto.setCreatedBy(1L);
        authorityDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        authorityDto.setUpdatedBy(1L);
        authorityDto.setDeleted(false);
        Mockito.when(authorityService.createAuthority(authorityCreateDto)).thenReturn(authorityDto);
    }

}
