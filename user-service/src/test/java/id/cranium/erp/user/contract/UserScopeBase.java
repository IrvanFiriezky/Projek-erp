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
import id.cranium.erp.user.controller.ScopeController;
import id.cranium.erp.user.service.ScopeService;
import id.cranium.erp.user.service.UserScopeService;
import id.cranium.erp.user.dto.ScopeDto;
import id.cranium.erp.user.dto.ScopeCreateDto;
import id.cranium.erp.user.dto.UserScopeMeDto;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.exception.RestResponseEntityExceptionHandler;
import id.cranium.erp.starter.test.security.annotation.WithMockCustomUser;
import id.cranium.erp.user.UserSpringBootTest;

//@WithMockUser(username = "user-01", authorities = {"DOMAIN_MENU_PERMISSION","USER_SCOPE_READ"})
@WithMockCustomUser(userId = 12L, username = "user-12", authorities = {"DOMAIN_MENU_PERMISSION","USER_SCOPE_READ"})
public class UserScopeBase extends UserSpringBootTest {
    
    @MockBean
    private ScopeService scopeService;

    @MockBean
    private UserScopeService userScopeService;
    
    @Autowired
    private ScopeController scopeController;

    @Autowired
    private ResourceBundleMessageSource starterMessageSource;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(scopeController)
            .setControllerAdvice(new RestResponseEntityExceptionHandler(starterMessageSource))
            .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain));
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        getScopeById_should_be_success();
        getScopeById_should_be_failed();
        createScope_should_be_success();
        getMyScopeByName_should_be_success();
        getMyScope_should_be_success();
    }
    
    private void getScopeById_should_be_success() {
        ScopeDto scopeDto = new ScopeDto();
        scopeDto.setId(1L);
        scopeDto.setScopeName("DOMAIN_SCOPE_TEST");
        scopeDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        scopeDto.setCreatedBy(1L);
        scopeDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        scopeDto.setUpdatedBy(1L);
        scopeDto.setDeleted(false);
        Mockito.when(scopeService.findById(1L)).thenReturn(scopeDto);
    }

    private void getScopeById_should_be_failed() {
        Mockito.when(scopeService.findById(20L)).thenThrow(new DataNotFoundException("Scope dengan id 20 tidak ada"));
    }

    private void createScope_should_be_success() {
        String scopeName = "DOMAIN_SCOPE_NEW";

        ScopeCreateDto scopeCreateDto = ScopeCreateDto.builder()
            .scopeName(scopeName)
            .build();

        ScopeDto scopeDto = new ScopeDto();
        scopeDto.setId(2L);
        scopeDto.setScopeName(scopeName);
        scopeDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        scopeDto.setCreatedBy(1L);
        scopeDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        scopeDto.setUpdatedBy(1L);
        scopeDto.setDeleted(false);
        Mockito.when(scopeService.createScope(scopeCreateDto)).thenReturn(scopeDto);
    }

    private void getMyScopeByName_should_be_success() {
        String scopeName = "USER_SCOPE_OWN";

        UserScopeMeDto userScopeHasDto = new UserScopeMeDto();
        userScopeHasDto.setScopeName(scopeName);
        userScopeHasDto.setScopeValue("12");
        Mockito.when(userScopeService.findMyScopeByName(scopeName)).thenReturn(userScopeHasDto);
    }

    private void getMyScope_should_be_success() {
        String scopeName = "USER_SCOPE_OWN";

        List<UserScopeMeDto> userScopeHasDtoList = new ArrayList<UserScopeMeDto>();
        UserScopeMeDto userScopeHasDto = new UserScopeMeDto();
        userScopeHasDto.setScopeName(scopeName);
        userScopeHasDto.setScopeValue("12");
        userScopeHasDtoList.add(userScopeHasDto);

        Mockito.when(userScopeService.findMyScope()).thenReturn(userScopeHasDtoList);
    }

}
