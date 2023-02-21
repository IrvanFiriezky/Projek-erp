package id.cranium.erp.inventory.eventlistener.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import id.cranium.erp.inventory.service.BookshelfService;
import id.cranium.erp.starter.event.CronEvent;
import id.cranium.erp.starter.eventlistener.CronEventListener;
import id.cranium.erp.starter.security.UserAuthInfo;
import id.cranium.erp.starter.service.NotificationCronService;
import id.cranium.erp.inventory.event.BookshelfPriceCronEvent;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookshelfPriceCronEventListeners extends CronEventListener {

  @Autowired
  private NotificationCronService notificationCronService;

  @Autowired
  private BookshelfService bookshelfService;

  @EventListener({ BookshelfPriceCronEvent.class })
  public void handler(CronEvent cronEvent) {
    BookshelfPriceCronEvent event = (BookshelfPriceCronEvent) cronEvent;
    setRequestId(event);
    UserAuthInfo.setCronUserAuthInfo(event);

    log.info(
        "Cron BookshelfPriceCronEventListener execute: "
            + UserAuthInfo.getUserAuthInfo()
            + " - "
            + event.getJobId());
  }
}
