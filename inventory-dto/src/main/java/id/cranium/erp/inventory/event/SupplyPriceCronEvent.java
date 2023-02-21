package id.cranium.erp.inventory.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import id.cranium.erp.starter.event.CronEvent;

@Getter
@SuperBuilder(toBuilder = true)
public class SupplyPriceCronEvent extends CronEvent {
    
    private Long id;
    private String supplyName;
    private Long version;
}
