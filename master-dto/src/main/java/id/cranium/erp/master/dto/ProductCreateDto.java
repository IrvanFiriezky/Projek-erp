package id.cranium.erp.master.dto;

import lombok.Data;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@Builder
@Jacksonized
public class ProductCreateDto {
    
    @NotNull(message = "{master.productname.notnull}")
    @NotEmpty(message = "{master.productname.notempty}")
    private String productName;
    private int status;
    private Long totalStock;
}
