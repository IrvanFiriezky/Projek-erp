package id.cranium.erp.inventory.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.test.context.event.RecordApplicationEvents;

import id.cranium.erp.inventory.InventorySpringBootTest;
import id.cranium.erp.starter.service.StarterUserScopeService;

@RecordApplicationEvents
public class BookshelfServiceTest extends InventorySpringBootTest {

  @Autowired
  private ApplicationEvent applicationEvent;

  @Autowired
  private BookshelfService bookshelfService;

  @MockBean
  private StarterUserScopeService starterUserScopeService;

  @BeforeEach
  public void setup() {
    // Mockito.when(starterUserScopeService.getMyScopeValue(null))
  }
}
