package id.cranium.erp.user.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import id.cranium.erp.user.entity.Scope;
import java.util.Optional;

@Repository
public interface ScopeRepository extends JpaRepository<Scope, Long>, RevisionRepository<Scope, Long, Long> {
    
    Optional<Scope> findById(long id);
}
