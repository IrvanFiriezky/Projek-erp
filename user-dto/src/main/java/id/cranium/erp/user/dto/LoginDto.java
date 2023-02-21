package id.cranium.erp.user.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Data
public class LoginDto {
    
    private String accessToken;
    private Long userId;
    private String username;
    private boolean status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Long createdBy;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private boolean isDeleted;
}
