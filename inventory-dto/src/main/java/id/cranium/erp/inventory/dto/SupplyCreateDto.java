package id.cranium.erp.inventory.dto;

import lombok.Data;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@Jacksonized
public class SupplyCreateDto {
    
    @NotNull(message = "{inventory.supplyname.notnull}")
    @NotEmpty(message = "{inventory.supplyname.notempty}")
    private String supplyName;
    private int status;
    private Long totalStock;
}
