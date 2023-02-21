package id.cranium.erp.inventory.util;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import id.cranium.erp.inventory.service.BookshelfService;
import jakarta.annotation.PostConstruct;

@Component
public class BookshelfServiceUtil {

  private static BookshelfServiceUtil instance;

  @Autowired
  private BookshelfService bookshelfService;

  @PostConstruct
  public void fillInstance() {
    instance = this;
  }

  public static BookshelfService getBookshelfService() {
    return instance.bookshelfService;
  }

}
