package id.cranium.erp.user.client.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import id.cranium.erp.user.dto.LoginDto;
import id.cranium.erp.starter.exception.DataNotFoundException;

@Component
public class UserAuthRestClient {
    
    @Autowired
    @Qualifier("internalRestTemplate")
    private RestTemplate internalRestTemplate;

    @Value("${user.auth-service.url.verification.token}")
    private String authServiceUrlLogin;

    public LoginDto getLoginByAccessToken(String accessToken) throws DataNotFoundException {
        String url = authServiceUrlLogin + "/" + accessToken;
        return internalRestTemplate.getForObject(url, LoginDto.class);
	}
}