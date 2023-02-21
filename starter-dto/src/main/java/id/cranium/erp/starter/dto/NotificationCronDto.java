package id.cranium.erp.starter.dto;

import lombok.Data;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class NotificationCronDto {
    
    private String status;
    private String message;
}
