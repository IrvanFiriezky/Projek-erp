package id.cranium.erp.starter.configuration;

import org.springframework.data.domain.AuditorAware;
import java.util.Optional;
import id.cranium.erp.starter.security.UserAuthInfo;

public class AuditorAwareImpl implements AuditorAware<Long> {
    
    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.of(UserAuthInfo.getUserAuthInfo().getUserId());
    }
}

