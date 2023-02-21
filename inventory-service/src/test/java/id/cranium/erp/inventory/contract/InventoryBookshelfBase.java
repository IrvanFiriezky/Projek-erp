package id.cranium.erp.inventory.contract;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import id.cranium.erp.inventory.controller.BookshelfController;
import id.cranium.erp.inventory.dto.BookshelfDto;
import id.cranium.erp.inventory.service.BookshelfService;
import id.cranium.erp.starter.exception.RestResponseEntityExceptionHandler;
import id.cranium.erp.starter.test.security.annotation.WithMockCustomUser;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import id.cranium.erp.inventory.InventorySpringBootTest;

@WithMockCustomUser(userId = 12L, username = "user-12", authorities = { "DOMAIN_MENU_PERMISSION",
    "INVENTORY_PRODUCT_READ" })
public class InventoryBookshelfBase extends InventorySpringBootTest {
  @MockBean
  private BookshelfService bookshelfService;

  @Autowired
  private BookshelfController bookshelfController;

  @Autowired
  private ResourceBundleMessageSource starterMessageSource;

  @Autowired
  FilterChainProxy springSecurFilterChain;

  @BeforeEach
  public void setup() {
    StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(bookshelfController)
        .setControllerAdvice(new RestResponseEntityExceptionHandler(starterMessageSource))
        .apply(SecurityMockMvcConfigurers.springSecurity(springSecurFilterChain));

    RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    getBookshelfById_should_be_success();
  }

  private void getBookshelfById_should_be_success() {
    BookshelfDto bookshelfDto = new BookshelfDto();
    bookshelfDto.setId(1L);
    bookshelfDto.setBookshelfName("Test bookshelf name 1");
    bookshelfDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
    bookshelfDto.setCreatedBy(1L);
    bookshelfDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
    bookshelfDto.setUpdatedBy(1L);
    bookshelfDto.setDeleted(false);
    Mockito.when(bookshelfService.findById(1L)).thenReturn(bookshelfDto);
  }

}
