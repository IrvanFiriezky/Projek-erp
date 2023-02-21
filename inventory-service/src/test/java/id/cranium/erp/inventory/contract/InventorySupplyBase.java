package id.cranium.erp.inventory.contract;

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
import id.cranium.erp.inventory.controller.SupplyController;
import id.cranium.erp.inventory.service.SupplyService;
import id.cranium.erp.inventory.dto.SupplyCreateDto;
import id.cranium.erp.inventory.dto.SupplyDto;
import id.cranium.erp.inventory.enums.SupplyStatus;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.exception.RestResponseEntityExceptionHandler;
import id.cranium.erp.starter.test.security.annotation.WithMockCustomUser;
import id.cranium.erp.inventory.InventorySpringBootTest;

@WithMockCustomUser(userId = 12L, username = "user-12", authorities = {"DOMAIN_MENU_PERMISSION","INVENTORY_SUPPLY_READ"})
//@ActiveProfiles("inventory_test")
public class InventorySupplyBase extends InventorySpringBootTest {
    
    @MockBean
    private SupplyService supplyService;
    
    @Autowired
    private SupplyController supplyController;

    @Autowired
    private ResourceBundleMessageSource starterMessageSource;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(supplyController)
            .setControllerAdvice(new RestResponseEntityExceptionHandler(starterMessageSource))
            .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain));
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        getSupplyById_should_be_success();
        getSupplyById_should_be_failed();
        createSupply_should_be_success();
    }

    private void getSupplyById_should_be_success() {
        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setId(1L);
        supplyDto.setSupplyName("Test supply name 1");
        supplyDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        supplyDto.setCreatedBy(1L);
        supplyDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        supplyDto.setUpdatedBy(1L);
        supplyDto.setDeleted(false);
        Mockito.when(supplyService.findById(1L)).thenReturn(supplyDto);
    }

    private void getSupplyById_should_be_failed() {
        Mockito.when(supplyService.findById(2L)).thenThrow(new DataNotFoundException("Produk dengan id 2 tidak ada"));
    }

    private void createSupply_should_be_success() {
        String supplyName = "Test supply name new";
        Long totalStock = 25L;

        SupplyCreateDto supplyCreateDto = SupplyCreateDto.builder()
            .supplyName(supplyName)
            .totalStock(totalStock)
            .build();

        SupplyDto supplyDto = new SupplyDto();
        supplyDto.setId(2L);
        supplyDto.setSupplyName(supplyName);
        supplyDto.setStatus(SupplyStatus.ACTIVE.getValue());
        supplyDto.setTotalStock(totalStock);
        supplyDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        supplyDto.setCreatedBy(1L);
        supplyDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        supplyDto.setUpdatedBy(1L);
        supplyDto.setDeleted(false);
        Mockito.when(supplyService.createSupply(supplyCreateDto)).thenReturn(supplyDto);
    }
    
}
