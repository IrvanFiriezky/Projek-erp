package id.cranium.erp.user.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.List;
import id.cranium.erp.user.entity.UserScope;
import id.cranium.erp.user.entity.custom.UserScopeMe;

@Repository
public interface UserScopeRepository extends JpaRepository<UserScope, Long>, RevisionRepository<UserScope, Long, Long> {
    
    Optional<UserScope> findById(long id);
    Optional<Page<UserScope>> findByUserId(long userId, Pageable pageable);

    String findScopeByUserIdQuery = "SELECT sc.scope_name AS scopeName, us.scope_value as scopeValue " + 
        "FROM \"user\".\"user_scope\" us INNER JOIN \"user\".\"scope\" sc ON sc.id = us.scope_id " + 
        "WHERE us.user_id = :userId";
    @Query(value = findScopeByUserIdQuery, nativeQuery = true)
    Optional<List<UserScopeMe>> findScopeByUserId(@Param("userId") long userId);

    String findMyScopeByNameQuery = "SELECT sc.scope_name AS scopeName, us.scope_value as scopeValue " + 
        "FROM \"user\".\"scope\" sc INNER JOIN \"user\".\"user_scope\" us ON us.user_id = :userId AND us.scope_id = sc.id " + 
        "WHERE sc.scope_name = :scopeName";
    @Query(value = findMyScopeByNameQuery, nativeQuery = true)
    Optional<UserScopeMe> findMyScopeByName(@Param("userId") long userId, @Param("scopeName") String scopeName);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    //@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "30000")}) //Postgresql does not support
    Optional<UserScope> findWithLockingById(Long id);
    
}
