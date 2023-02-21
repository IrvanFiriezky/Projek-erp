package id.cranium.erp.starter.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import id.cranium.erp.starter.event.CronEvent;

@Getter
@SuperBuilder(toBuilder = true)
public class NotificationCronEvent extends CronEvent {
    
    private String status;
    private String message;
}
