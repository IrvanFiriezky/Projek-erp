package id.cranium.erp.starter.client.auth;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
//import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import id.cranium.erp.starter.StarterSpringBootTest;
import id.cranium.erp.starter.dto.LoginDto;
import id.cranium.erp.starter.configuration.StarterTestConfiguration;

@AutoConfigureStubRunner(
    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = "id.cranium.erp.user:user-service:+:stubs:8090")
    //ids = {"id.cranium.erp.user:user-service:+:stubs:8090", "id.cranium.erp.master:master-service:+:stubs:8091"})
@Import(StarterTestConfiguration.class)
//@ActiveProfiles("master_test")
public class StarterAuthWebClientTest extends StarterSpringBootTest {
    
    @Autowired
    private StarterAuthWebClient starterAuthWebClient;

    @Test
    public void given_AccessToken_ThenReturnSuccess() throws Exception {
        String accessToken = "11111111-2222-3333-4444-555555555555";

        LoginDto loginDto = starterAuthWebClient.getLoginByAccessToken(accessToken);
        assertThat(loginDto).isNotNull();
        assertThat(loginDto.getUserId()).isEqualTo(12L);
        assertThat(loginDto.getUsername()).isEqualTo("user-test");
        assertThat(loginDto.getAccessToken()).isEqualTo(accessToken);
    }
    
}
