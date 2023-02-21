package id.cranium.erp.master.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import id.cranium.erp.starter.event.BaseEvent;

@Getter
@SuperBuilder(toBuilder = true)
public class ProductCreateEvent extends BaseEvent {
    
    private Long id;
    private String productName;
}
