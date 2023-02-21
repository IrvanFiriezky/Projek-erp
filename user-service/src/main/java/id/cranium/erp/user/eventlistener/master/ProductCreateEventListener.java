package id.cranium.erp.user.eventlistener.master;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.master.event.ProductCreateEvent;
import id.cranium.erp.starter.event.BaseEvent;
import id.cranium.erp.starter.eventlistener.BaseEventListener;

@Component
@Slf4j
public class ProductCreateEventListener extends BaseEventListener {
    
    @Async
    @EventListener({ProductCreateEvent.class})
    public void handler(BaseEvent baseEvent) {
         
        ProductCreateEvent event = (ProductCreateEvent) baseEvent;
        setRequestId(event);
        log.info("Auditing the event ProductCreateEvent: " + event.getXRequestId() + " - " + event.getXUserId() + " - " + event.getId() + " - " + event.getProductName());
    }
}
