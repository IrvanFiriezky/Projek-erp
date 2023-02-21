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
import id.cranium.erp.user.controller.UserController;
import id.cranium.erp.user.service.UserService;
import id.cranium.erp.user.dto.UserDto;
import id.cranium.erp.user.dto.UserCreateDto;
import id.cranium.erp.user.enums.UserStatus;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.exception.RestResponseEntityExceptionHandler;
import id.cranium.erp.starter.test.security.annotation.WithMockCustomUser;
import id.cranium.erp.user.UserSpringBootTest;

//@WithMockUser(username = "user-01", authorities = {"DOMAIN_MENU_PERMISSION","USER_PROFILE_READ"})
@WithMockCustomUser(userId = 12L, username = "user-12", authorities = {"DOMAIN_MENU_PERMISSION","USER_PROFILE_READ"})
public class UserUserBase extends UserSpringBootTest {
    
    @MockBean
    private UserService userService;
    
    @Autowired
    private UserController userController;

    @Autowired
    private ResourceBundleMessageSource starterMessageSource;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new RestResponseEntityExceptionHandler(starterMessageSource))
            .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain));
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        getUserById_should_be_success();
        getUserById_should_be_failed();
        createUser_should_be_success();
    }

    private void getUserById_should_be_success() {
        UserDto userDto = new UserDto();
        userDto.setId(12L);
        userDto.setUsername("user-test");
        userDto.setFirstName("Test firstname");
        userDto.setMiddleName("Test middlename");
        userDto.setLastName("Test lastname");
        userDto.setEmail("user-test@cranium.id");
        userDto.setMobilePhone("08311111111");
        userDto.setDomain("user");
        userDto.setStatus(UserStatus.ACTIVE.getValue());
        userDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        userDto.setCreatedBy(1L);
        userDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        userDto.setUpdatedBy(1L);
        userDto.setDeleted(false);
        Mockito.when(userService.findById(12L)).thenReturn(userDto);
    }

    private void getUserById_should_be_failed() {
        Mockito.when(userService.findById(20L)).thenThrow(new DataNotFoundException("User dengan id 20 tidak ada"));
    }

    private void createUser_should_be_success() {
        String username = "user-test-new";
        String password = "pass-test-new";
        String firstName = "Test First";
        String middleName = "Middle";
        String lastName = "Last";
        String email = "user-test-new@cranium.id";
        String mobilePhone = "08111111111";
        String domain = "test";
        int status = 1;

        UserCreateDto userCreateDto = UserCreateDto.builder()
            .username(username)
            .password(password)
            .firstName(firstName)
            .middleName(middleName)
            .lastName(lastName)
            .email(email)
            .mobilePhone(mobilePhone)
            .domain(domain)
            .status(status)
            .build();

        UserDto userDto = new UserDto();
        userDto.setId(2L);
        userDto.setUsername(username);
        userDto.setFirstName(firstName);
        userDto.setMiddleName(middleName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setMobilePhone(mobilePhone);
        userDto.setDomain(domain);
        userDto.setStatus(UserStatus.ACTIVE.getValue());
        userDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        userDto.setCreatedBy(1L);
        userDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        userDto.setUpdatedBy(1L);
        userDto.setDeleted(false);
        Mockito.when(userService.createUserProfile(userCreateDto)).thenReturn(userDto);
    }

}
