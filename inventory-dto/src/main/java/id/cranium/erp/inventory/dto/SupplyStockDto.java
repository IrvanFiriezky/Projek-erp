package id.cranium.erp.inventory.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class SupplyStockDto {
    
    private String supplyName;
    private Long totalStock;
}
