package id.cranium.erp.cron.eventlistener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.master.event.ProductPriceCronEvent;
import id.cranium.erp.starter.event.CronEvent;
import id.cranium.erp.starter.event.NotificationCronEvent;
import id.cranium.erp.starter.eventlistener.CronEventListener;

@Component
@Slf4j
public class NotificationCronEventListener extends CronEventListener {
    
    @EventListener({NotificationCronEvent.class})
    public void handler(CronEvent cronEvent) {
         
        NotificationCronEvent event = (NotificationCronEvent) cronEvent;
        setRequestId(event);
        log.info("Cron NotificationCronEventListener execute: " + event.getJobId() + " - " + event.getStatus() + " - " + event.getMessage());
    }
}
