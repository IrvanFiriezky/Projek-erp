package id.cranium.erp.inventory.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import id.cranium.erp.inventory.validator.constraint.InventorySupplyIsExists;

@Data
@Builder
@Jacksonized
public class StockUpdateDto {
    
    @InventorySupplyIsExists
    private Long supplyId;
    private Long totalStock;
}
