package id.cranium.erp.master.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import id.cranium.erp.master.entity.Stock;
import id.cranium.erp.master.entity.custom.ProductStock;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    
    Optional<Stock> findById(Long id);
    Optional<Stock> findByIdAndCreatedBy(Long id, Long createdBy);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Stock> findWithLockingById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Stock> findWithLockingByIdAndCreatedBy(Long id, Long createdBy);

    String findStockProductByIdQuery = "SELECT pr.product_name AS productName, st.total_stock as totalStock " + 
        "FROM \"master\".\"product\" pr INNER JOIN \"master\".\"stock\" st ON st.product_id = pc.id " + 
        "WHERE pr.id = :productId";
    @Query(value = findStockProductByIdQuery, nativeQuery = true)
    Optional<ProductStock> findStockProductById(@Param("productId") long productId);
}
