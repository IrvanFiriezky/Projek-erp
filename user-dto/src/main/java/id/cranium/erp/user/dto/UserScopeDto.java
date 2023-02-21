package id.cranium.erp.user.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
public class UserScopeDto {
    
    private Long id;
    private Long userId;
    private Long scopeId;
    private String scopeName;
    private String scopeValue;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Long createdBy;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private boolean isDeleted;
}
