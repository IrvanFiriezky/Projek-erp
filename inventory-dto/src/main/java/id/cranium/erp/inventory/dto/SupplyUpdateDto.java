package id.cranium.erp.inventory.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class SupplyUpdateDto {
    
    @NotNull(message = "{inventory.supplyname.notnull}")
    @NotEmpty(message = "{inventory.supplyname.notempty}")
    private String supplyName;
    private Long version;
}
