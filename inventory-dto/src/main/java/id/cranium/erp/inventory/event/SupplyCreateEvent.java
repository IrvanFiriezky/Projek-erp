package id.cranium.erp.inventory.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import id.cranium.erp.starter.event.BaseEvent;

@Getter
@SuperBuilder(toBuilder = true)
public class SupplyCreateEvent extends BaseEvent {
    
    private Long id;
    private String supplyName;
}
