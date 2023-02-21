package id.cranium.erp.inventory.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
public class StockDto {
    
    private Long id;
    private Long supplyId;
    private Long totalStock;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Long createdBy;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private boolean deleted;
}
