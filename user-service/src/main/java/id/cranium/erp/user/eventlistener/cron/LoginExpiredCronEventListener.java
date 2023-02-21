package id.cranium.erp.user.eventlistener.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.user.event.LoginExpiredCronEvent;
import id.cranium.erp.starter.event.CronEvent;
import id.cranium.erp.starter.eventlistener.CronEventListener;
import id.cranium.erp.starter.dto.NotificationCronDto;
import id.cranium.erp.starter.enums.NotificationCronStatus;
import id.cranium.erp.starter.service.NotificationCronService;
import id.cranium.erp.starter.security.UserAuthInfo;

@Component
@Slf4j
public class LoginExpiredCronEventListener extends CronEventListener {

    @Autowired
    private NotificationCronService notificationCronService;
    
    @EventListener({LoginExpiredCronEvent.class})
    public void handler(CronEvent cronEvent) {
         
        LoginExpiredCronEvent event = (LoginExpiredCronEvent) cronEvent;
        setRequestId(event);
        UserAuthInfo.setCronUserAuthInfo(event);
        log.info("Cron LoginExpiredCronEventListener execute: " + UserAuthInfo.getUserAuthInfo() + " - " + event.getJobId());

        String status = NotificationCronStatus.SUCCESS.getValue();
        String message = "";
        NotificationCronDto notificationCronDto = NotificationCronDto.builder()
            .status(status)
            .message(message)
            .build();
        notificationCronService.sendNotificationCron(event, notificationCronDto);
    }

}
