package id.cranium.erp.master.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;
import id.cranium.erp.master.entity.Product;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findById(Long id);
    Optional<Product> findByIdAndCreatedBy(Long id, Long createdBy);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Product> findWithLockingById(Long id);

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Product> findWithLockingByIdAndCreatedBy(Long id, Long createdBy);
}
