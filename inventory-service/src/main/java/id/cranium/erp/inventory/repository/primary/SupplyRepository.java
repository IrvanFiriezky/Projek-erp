package id.cranium.erp.inventory.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;
import id.cranium.erp.inventory.entity.Supply;
import java.util.Optional;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    
    Optional<Supply> findById(Long id);
    Optional<Supply> findByIdAndCreatedBy(Long id, Long createdBy);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Supply> findWithLockingById(Long id);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Supply> findWithLockingByIdAndCreatedBy(Long id, Long createdBy);
}
