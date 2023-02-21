package id.cranium.erp.inventory.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import id.cranium.erp.inventory.entity.Stock;
import id.cranium.erp.inventory.entity.custom.SupplyStock;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    
    Optional<Stock> findById(Long id);
    Optional<Stock> findByIdAndCreatedBy(Long id, Long createdBy);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Stock> findWithLockingById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Stock> findWithLockingByIdAndCreatedBy(Long id, Long createdBy);

    String findStockSupplyByIdQuery = "SELECT pr.supply_name AS supplyName, st.total_stock as totalStock " +
        "FROM \"inventory\".\"supply\" pr INNER JOIN \"inventory\".\"stock\" st ON st.supply_id = pc.id " +
        "WHERE pr.id = :supplyId";
    @Query(value = findStockSupplyByIdQuery, nativeQuery = true)
    Optional<SupplyStock> findStockSupplyById(@Param("supplyId") long supplyId);
}
