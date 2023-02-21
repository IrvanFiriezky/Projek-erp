package id.cranium.erp.starter.eventlistener;

import org.slf4j.MDC;
import id.cranium.erp.starter.event.BaseEvent;
import id.cranium.erp.starter.configuration.CorrelationConfiguration;

public abstract class BaseEventListener {
    
    public abstract void handler(BaseEvent event);

    protected void setRequestId(BaseEvent event) {
        if (!event.getXRequestId().isEmpty()) {
            MDC.put(CorrelationConfiguration.REQUEST_ID_HEADER_NAME, event.getXRequestId());
        }
    }
}
