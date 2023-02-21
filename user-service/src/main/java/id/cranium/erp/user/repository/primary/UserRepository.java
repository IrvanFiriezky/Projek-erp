package id.cranium.erp.user.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import id.cranium.erp.user.entity.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, RevisionRepository<User, Long, Long> {
    Optional<User> findById(Long id);
    Optional<Page<User>> findByDeleted(boolean deleted, Pageable pageable);
}
