package id.cranium.erp.master.client.user;

public class MasterUserWebClientTest {

}
/*
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
//import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.Month;
import id.cranium.erp.master.MasterSpringBootTest;
import id.cranium.erp.starter.dto.ApiErrorDto;
import id.cranium.erp.user.dto.UserDto;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.starter.test.security.annotation.WithMockCustomUser;
import id.cranium.erp.master.configuration.MasterTestConfiguration;

@AutoConfigureStubRunner(
    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = "id.cranium.erp.user:user-service:+:stubs:8090")
@WithMockCustomUser(userId = 12L, username = "user-12", authorities = {"DOMAIN_MENU_PERMISSION","MASTER_PRODUCT_READ"})
@Import(MasterTestConfiguration.class)
//@ActiveProfiles("master_test")
public class MasterUserWebClientTest extends MasterSpringBootTest {
    
    @Autowired
    private MasterUserWebClient masterUserWebClient;

    @Test
    public void given_GetUserById_ThenReturnSuccess() throws Exception {
        UserDto userDto = masterUserWebClient.getUserById(12L);
        assertThat(userDto).isNotNull();
        assertThat(userDto.getId()).isEqualTo(12L);
        assertThat(userDto.getUsername()).isEqualTo("user-test");
        assertThat(userDto.getFirstName()).isEqualTo("Test firstname");
        assertThat(userDto.getMiddleName()).isEqualTo("Test middlename");
        assertThat(userDto.getLastName()).isEqualTo("Test lastname");
        assertThat(userDto.getEmail()).isEqualTo("user-test@cranium.id");
        assertThat(userDto.getMobilePhone()).isEqualTo("08311111111");
        assertThat(userDto.getDomain()).isEqualTo("user");
        assertThat(userDto.getStatus()).isEqualTo(1);
        assertThat(userDto.getCreatedAt()).isEqualTo(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        assertThat(userDto.getCreatedBy()).isEqualTo(1L);
        assertThat(userDto.getUpdatedAt()).isEqualTo(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        assertThat(userDto.getUpdatedBy()).isEqualTo(1L);
        assertThat(userDto.isDeleted()).isEqualTo(false);
    }

   @Test
    public void given_GetUserById_ThenReturnNotFound() throws Exception {
        ClientException exception = assertThrows(ClientException.class, () -> masterUserWebClient.getUserById(20L));
        ApiErrorDto apiErrorDto = new ObjectMapper().readValue(exception.getMessage(), ApiErrorDto.class);
        assertThat(apiErrorDto).isNotNull();
        assertThat(apiErrorDto.getStatus().value()).isEqualTo(404);
        assertThat(apiErrorDto.getMessage()).isEqualTo("User dengan id 20 tidak ada");
    }
    
}
*/