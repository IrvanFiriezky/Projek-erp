package id.cranium.erp.starter.service;

import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.starter.dto.NotificationCronDto;
import id.cranium.erp.starter.event.CronEvent;
import id.cranium.erp.starter.event.NotificationCronEvent;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationCronService {

    private final ApplicationEventPublisher notificationPublisher;
    
    public void sendNotificationCron(CronEvent cronEvent, NotificationCronDto notificationCronDto) {
        NotificationCronEvent notificationCronEvent = NotificationCronEvent.builder()
			.xUserId(cronEvent.getXUserId())
			.xRequestId(cronEvent.getXRequestId())
            .jobId(cronEvent.getJobId())
			.status(notificationCronDto.getStatus())
			.message(notificationCronDto.getMessage())
			.build();
        log.info("Cron NotificationCronService.sendNotificationCron: " + cronEvent.getJobId());
		notificationPublisher.publishEvent(notificationCronEvent);
    }
}
