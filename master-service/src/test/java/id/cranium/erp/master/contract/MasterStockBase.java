package id.cranium.erp.master.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import java.time.LocalDateTime;
import java.time.Month;
import id.cranium.erp.master.controller.StockController;
import id.cranium.erp.master.service.StockService;
import id.cranium.erp.master.service.ProductService;
import id.cranium.erp.master.dto.StockDto;
import id.cranium.erp.master.dto.StockUpdateDto;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.exception.RestResponseEntityExceptionHandler;
import id.cranium.erp.starter.test.security.annotation.WithMockCustomUser;
import id.cranium.erp.master.MasterSpringBootTest;

@WithMockCustomUser(userId = 12L, username = "user-12", authorities = {"DOMAIN_MENU_PERMISSION","MASTER_STOCK_READ"})
//@ActiveProfiles("master_test")
public class MasterStockBase extends MasterSpringBootTest {
    
    @MockBean
    private StockService stockService;

    @MockBean
    private ProductService productService;
    
    @Autowired
    private StockController stockController;

    @Autowired
    private ResourceBundleMessageSource starterMessageSource;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(stockController)
            .setControllerAdvice(new RestResponseEntityExceptionHandler(starterMessageSource))
            .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain));
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        getStockById_should_be_success();
        getStockById_should_be_failed();
        updateStockById_should_be_success();
    }

    private void getStockById_should_be_success() {
        StockDto stockDto = new StockDto();
        stockDto.setId(1L);
        stockDto.setProductId(1L);
        stockDto.setTotalStock(15L);
        stockDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        stockDto.setCreatedBy(1L);
        stockDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        stockDto.setUpdatedBy(1L);
        stockDto.setDeleted(false);
        Mockito.when(stockService.findById(1L)).thenReturn(stockDto);
    }

    private void getStockById_should_be_failed() {
        Mockito.when(stockService.findById(2L)).thenThrow(new DataNotFoundException("Stok dengan id 2 tidak ada"));
    }

    private void updateStockById_should_be_success() {
        Long stockId = 1L;
        Long totalStock = 25L;

        StockUpdateDto stockUpdateDto = StockUpdateDto.builder()
            .productId(1L)
            .totalStock(totalStock)
            .build();

        StockDto stockDto = new StockDto();
        stockDto.setId(stockId);
        stockDto.setProductId(1L);
        stockDto.setTotalStock(totalStock);
        stockDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        stockDto.setCreatedBy(1L);
        stockDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        stockDto.setUpdatedBy(1L);
        stockDto.setDeleted(false);
        Mockito.when(stockService.updateStock(stockUpdateDto, stockId)).thenReturn(stockDto);
    }
}
