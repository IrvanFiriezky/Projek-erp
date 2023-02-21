package id.cranium.erp.user.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import id.cranium.erp.starter.event.CronEvent;

@Getter
@SuperBuilder(toBuilder = true)
public class LoginExpiredCronEvent extends CronEvent {
    
}