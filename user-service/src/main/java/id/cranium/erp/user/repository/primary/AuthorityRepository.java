package id.cranium.erp.user.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import id.cranium.erp.user.entity.Authority;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>, RevisionRepository<Authority, Long, Long> {
    
    Optional<Authority> findById(long id);
}
