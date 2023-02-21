package id.cranium.erp.master.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import id.cranium.erp.master.validator.constraint.MasterProductIsExists;

@Data
@Builder
@Jacksonized
public class StockUpdateDto {
    
    @MasterProductIsExists
    private Long productId;
    private Long totalStock;
}
