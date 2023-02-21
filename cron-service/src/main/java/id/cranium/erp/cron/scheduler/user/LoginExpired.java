package id.cranium.erp.cron.scheduler.user;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import id.cranium.erp.user.event.LoginExpiredCronEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.starter.configuration.CorrelationConfiguration;
import id.cranium.erp.user.event.LoginExpiredCronEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginExpired {

    private final ApplicationEventPublisher userPublisher;

    @Value("${cron.spring.task.scheduling.userId}")
    private String cronSpringUserId;
    
    @Scheduled(cron = "*/30 * * * * *")
    public void deleteLoginExpired() {
        LoginExpiredCronEvent loginExpiredCronEvent = LoginExpiredCronEvent.builder()
			.xUserId(Long.parseLong(cronSpringUserId))
			.xRequestId(CorrelationConfiguration.generateAndSetMDCRequestId())
            .jobId(CorrelationConfiguration.generateUUID())
			.build();
        log.info("Cron LoginExpired.deleteLoginExpired run");
        userPublisher.publishEvent(loginExpiredCronEvent);
    }
}
