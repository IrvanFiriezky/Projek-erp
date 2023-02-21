package id.cranium.erp.starter.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.starter.client.user.StarterUserWebClient;
import id.cranium.erp.starter.dto.UserScopeMeDto;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.starter.exception.ServerException;

@Service
@Slf4j
@RequiredArgsConstructor
public class StarterUserScopeService {
    
    private final StarterUserWebClient starterUserWebClient;
    public static Optional<Map<String, String>> myScopes = Optional.ofNullable(null);

    public String getMyScopeValue(String scopeName) {

        try {
            UserScopeMeDto userScopeMeDto = starterUserWebClient.getMyScopeByName(scopeName);
            return userScopeMeDto.getScopeValue();
        } catch(ClientException ex) {
            return "";
        } catch(ServerException ex) {
            log.error("UserScopeService.getMyScopeValue: " + ex.getMessage());
            return "";
        }
    }

    public Map<String, String> getMyScopes() {
        if (myScopes.isPresent()) {
            return myScopes.get();
        } else {
            myScopes = Optional.of(new HashMap<String, String>());
        }

        try {
            List<UserScopeMeDto> userScopeMeDtoList = starterUserWebClient.getMyScope();
            userScopeMeDtoList.stream().map(userScopeMeDto -> myScopes.get().put(userScopeMeDto.getScopeName(), userScopeMeDto.getScopeValue()));
        } catch(ClientException ex) {
            
        } catch(ServerException ex) {
            log.error("UserScopeService.getMyScopes: " + ex.getMessage());
        }

        return myScopes.get();
    }

}
