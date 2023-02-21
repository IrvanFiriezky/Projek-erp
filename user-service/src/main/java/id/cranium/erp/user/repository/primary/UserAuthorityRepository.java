package id.cranium.erp.user.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import id.cranium.erp.user.entity.UserAuthority;
import java.util.Optional;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long>, RevisionRepository<UserAuthority, Long, Long> {
    
    Optional<UserAuthority> findById(long id);
    Optional<Page<UserAuthority>> findByUserId(long userId, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    //@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "30000")}) //Postgresql does not support
    Optional<UserAuthority> findWithLockingById(Long id);
    
}
