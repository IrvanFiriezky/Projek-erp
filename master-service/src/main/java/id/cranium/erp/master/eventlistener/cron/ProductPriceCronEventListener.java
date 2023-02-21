package id.cranium.erp.master.eventlistener.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.master.dto.ProductUpdateDto;
import id.cranium.erp.master.dto.ProductDto;
import id.cranium.erp.master.event.ProductPriceCronEvent;
import id.cranium.erp.master.service.ProductService;
import id.cranium.erp.starter.event.CronEvent;
import id.cranium.erp.starter.eventlistener.CronEventListener;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.starter.dto.NotificationCronDto;
import id.cranium.erp.starter.enums.NotificationCronStatus;
import id.cranium.erp.starter.service.NotificationCronService;
import id.cranium.erp.starter.security.UserAuthInfo;

@Component
@Slf4j
public class ProductPriceCronEventListener extends CronEventListener {

    @Autowired
    private NotificationCronService notificationCronService;

    @Autowired
    private ProductService productService;
    
    @EventListener({ProductPriceCronEvent.class})
    public void handler(CronEvent cronEvent) {
         
        ProductPriceCronEvent event = (ProductPriceCronEvent) cronEvent;
        setRequestId(event);
        UserAuthInfo.setCronUserAuthInfo(event);

        log.info("Cron ProductPriceCronEventListener execute: " + UserAuthInfo.getUserAuthInfo() + " - " + event.getJobId());

        ProductUpdateDto productUpdateDto = ProductUpdateDto.builder()
            .productName(event.getProductName())
            .version(event.getVersion())
            .build();
        
        String status = NotificationCronStatus.SUCCESS.getValue();
        String message = "";
        try {
            ProductDto product = productService.updateProduct(productUpdateDto, event.getId());
        } catch (ClientException | ObjectOptimisticLockingFailureException ex) {
            status = NotificationCronStatus.FAILED.getValue();
            message = ex.getMessage();
        }

        NotificationCronDto notificationCronDto = NotificationCronDto.builder()
            .status(status)
            .message(message)
            .build();
        notificationCronService.sendNotificationCron(event, notificationCronDto);
    }

}
