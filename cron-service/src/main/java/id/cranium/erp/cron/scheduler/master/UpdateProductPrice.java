package id.cranium.erp.cron.scheduler.master;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import id.cranium.erp.master.event.ProductPriceCronEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.starter.configuration.CorrelationConfiguration;
import id.cranium.erp.master.event.ProductPriceCronEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateProductPrice {
    
    private final ApplicationEventPublisher masterPublisher;

    @Value("${cron.spring.task.scheduling.userId}")
    private String cronSpringUserId;
    
    @Scheduled(cron = "0 30 15 * * *")
    public void updateProductPrice() {
        ProductPriceCronEvent productPriceCronEvent = ProductPriceCronEvent.builder()
			.xUserId(Long.parseLong(cronSpringUserId))
			.xRequestId(CorrelationConfiguration.generateAndSetMDCRequestId())
            .jobId(CorrelationConfiguration.generateUUID())
            .id(3L)
            .productName("Product 03 Cron")
            .version(25L)
			.build();
        log.info("Cron UpdateProductPrice.updateProductPrice run");
        masterPublisher.publishEvent(productPriceCronEvent);
    }

}
