package id.cranium.erp.starter.client.user;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
//import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import id.cranium.erp.starter.StarterSpringBootTest;
import id.cranium.erp.starter.dto.UserScopeMeDto;
import id.cranium.erp.starter.configuration.StarterTestConfiguration;

@AutoConfigureStubRunner(
    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = "id.cranium.erp.user:user-service:+:stubs:8090")
    //ids = {"id.cranium.erp.user:user-service:+:stubs:8090", "id.cranium.erp.master:master-service:+:stubs:8091"})
@Import(StarterTestConfiguration.class)
//@ActiveProfiles("master_test")
public class StarterUserWebClientTest extends StarterSpringBootTest {
    
    @Autowired
    private StarterUserWebClient starterUserWebClient;

    @Test
    public void given_ScopeName_ThenReturnSuccess() throws Exception {
        String scopeName = "USER_SCOPE_OWN";

        UserScopeMeDto userScopeHasDto = starterUserWebClient.getMyScopeByName(scopeName);
        assertThat(userScopeHasDto).isNotNull();
        assertThat(userScopeHasDto.getScopeName()).isEqualTo(scopeName);
        assertThat(userScopeHasDto.getScopeValue()).isEqualTo("12");
    }

    @Test
    public void given_UserId_ThenReturnSuccess() throws Exception {
        String scopeName = "USER_SCOPE_OWN";

        List<UserScopeMeDto> userScopeHasDtoList = starterUserWebClient.getMyScope();
        assertThat(userScopeHasDtoList).isNotNull();
        assertThat(userScopeHasDtoList.get(0).getScopeName()).isEqualTo(scopeName);
        assertThat(userScopeHasDtoList.get(0).getScopeValue()).isEqualTo("12");
    }

}
