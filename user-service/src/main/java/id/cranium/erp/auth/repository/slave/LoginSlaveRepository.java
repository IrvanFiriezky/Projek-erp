package id.cranium.erp.auth.repository.slave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.time.LocalDateTime;
import id.cranium.erp.user.entity.Login;

@Repository
public interface LoginSlaveRepository extends JpaRepository<Login, String> {
    
    Optional<Login> findByAccessToken(String accessToken);
    Optional<Login> findByAccessTokenAndStatusAndDeleted(String accessToken, boolean status, boolean deleted);
    Optional<Page<Login>> findByUsername(String username, Pageable pageable);
    Optional<Page<Login>> findByCreatedAtGreaterThan(LocalDateTime createdAt, Pageable pageable);
}
