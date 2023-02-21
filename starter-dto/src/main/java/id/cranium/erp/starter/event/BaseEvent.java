package id.cranium.erp.starter.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class BaseEvent {
    
    private String xRequestId;
    private Long xUserId;
}
