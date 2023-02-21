package id.cranium.erp.auth.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import id.cranium.erp.user.entity.User;
import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndDomain(String username, String domain);
}
