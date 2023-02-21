package id.cranium.erp.inventory.eventlistener.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.inventory.dto.StockUpdateDto;
import id.cranium.erp.inventory.dto.SupplyDto;
import id.cranium.erp.inventory.event.SupplyPriceCronEvent;
import id.cranium.erp.inventory.service.SupplyService;
import id.cranium.erp.starter.event.CronEvent;
import id.cranium.erp.starter.eventlistener.CronEventListener;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.starter.dto.NotificationCronDto;
import id.cranium.erp.starter.enums.NotificationCronStatus;
import id.cranium.erp.starter.service.NotificationCronService;
import id.cranium.erp.starter.security.UserAuthInfo;

@Component
@Slf4j
public class SupplyPriceCronEventListener extends CronEventListener {

    @Autowired
    private NotificationCronService notificationCronService;

    @Autowired
    private SupplyService supplyService;
    
    @EventListener({SupplyPriceCronEvent.class})
    public void handler(CronEvent cronEvent) {
         
        SupplyPriceCronEvent event = (SupplyPriceCronEvent) cronEvent;
        setRequestId(event);
        UserAuthInfo.setCronUserAuthInfo(event);

        log.info("Cron SupplyPriceCronEventListener execute: " + UserAuthInfo.getUserAuthInfo() + " - " + event.getJobId());

        StockUpdateDto stockUpdateDto = StockUpdateDto.builder()
            .supplyName(event.getSupplyName())
            .version(event.getVersion())
            .build();
        
        String status = NotificationCronStatus.SUCCESS.getValue();
        String message = "";
        try {
            SupplyDto supply = supplyService.updateSupply(stockUpdateDto, event.getId());
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
