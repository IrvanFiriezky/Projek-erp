package id.cranium.erp.master.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ProductStockDto {
    
    private String productName;
    private Long totalStock;
}
