package id.cranium.erp.master.event;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import id.cranium.erp.starter.event.CronEvent;

@Getter
@SuperBuilder(toBuilder = true)
public class ProductPriceCronEvent extends CronEvent {
    
    private Long id;
    private String productName;
    private Long version;
}
