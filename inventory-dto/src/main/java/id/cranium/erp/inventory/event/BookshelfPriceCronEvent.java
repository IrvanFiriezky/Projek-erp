package id.cranium.erp.inventory.event;

import id.cranium.erp.starter.event.CronEvent;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class BookshelfPriceCronEvent extends CronEvent {
  private Long id;
  private String bookshelfName;
  private Long version;
}
