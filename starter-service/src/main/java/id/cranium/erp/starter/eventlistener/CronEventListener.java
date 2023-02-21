package id.cranium.erp.starter.eventlistener;

import org.slf4j.MDC;
import id.cranium.erp.starter.event.CronEvent;
import id.cranium.erp.starter.configuration.CorrelationConfiguration;

public abstract class CronEventListener {
    
    public abstract void handler(CronEvent event);

    protected void setRequestId(CronEvent event) {
        if (!event.getXRequestId().isEmpty()) {
            MDC.put(CorrelationConfiguration.REQUEST_ID_HEADER_NAME, event.getXRequestId());
        }
    }
}
