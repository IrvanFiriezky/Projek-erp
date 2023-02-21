package id.cranium.erp.user.client.master;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
//import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.test.context.support.WithMockUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.Month;
import id.cranium.erp.user.UserSpringBootTest;
import id.cranium.erp.starter.dto.ApiErrorDto;
import id.cranium.erp.master.dto.ProductDto;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.user.configuration.UserTestConfiguration;

@AutoConfigureStubRunner(
    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = "id.cranium.erp.master:master-service:+:stubs:8091")
@WithMockUser(username = "user-01", authorities = {"DOMAIN_MENU_PERMISSION","MASTER_PRODUCT_READ"})
@Import(UserTestConfiguration.class)
//@ActiveProfiles("master_test")
public class UserProductWebClientTest extends UserSpringBootTest {
    
    @Autowired
    private UserProductWebClient userProductWebClient;

    @Test
    public void given_GetProductById_ThenReturnSuccess() throws Exception {
        ProductDto productDto = userProductWebClient.getProductById(1L);
        assertThat(productDto).isNotNull();
        assertThat(productDto.getId()).isEqualTo(1L);
        assertThat(productDto.getProductName()).isEqualTo("Test product name 1");
        assertThat(productDto.getCreatedAt()).isEqualTo(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        assertThat(productDto.getCreatedBy()).isEqualTo(1L);
        assertThat(productDto.getUpdatedAt()).isEqualTo(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        assertThat(productDto.getUpdatedBy()).isEqualTo(1L);
        assertThat(productDto.isDeleted()).isEqualTo(false);
    }

   @Test
    public void given_GetProductById_ThenReturnNotFound() throws Exception {
        ClientException exception = assertThrows(ClientException.class, () -> userProductWebClient.getProductById(2L));
        ApiErrorDto apiErrorDto = new ObjectMapper().readValue(exception.getMessage(), ApiErrorDto.class);
        assertThat(apiErrorDto).isNotNull();
        assertThat(apiErrorDto.getStatus().value()).isEqualTo(404);
        assertThat(apiErrorDto.getMessage()).isEqualTo("Produk dengan id 2 tidak ada");
    }
    
}
