package id.cranium.erp.starter.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import id.cranium.erp.starter.event.BaseEvent;

@Getter
@SuperBuilder(toBuilder = true)
public class CronEvent extends BaseEvent {
    
    private String jobId;
}
