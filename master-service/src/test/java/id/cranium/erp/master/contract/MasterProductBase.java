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
import id.cranium.erp.master.controller.ProductController;
import id.cranium.erp.master.service.ProductService;
import id.cranium.erp.master.dto.ProductCreateDto;
import id.cranium.erp.master.dto.ProductDto;
import id.cranium.erp.master.enums.ProductStatus;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.exception.RestResponseEntityExceptionHandler;
import id.cranium.erp.starter.test.security.annotation.WithMockCustomUser;
import id.cranium.erp.master.MasterSpringBootTest;

@WithMockCustomUser(userId = 12L, username = "user-12", authorities = { "DOMAIN_MENU_PERMISSION",
        "MASTER_PRODUCT_READ" })
// @ActiveProfiles("master_test")
public class MasterProductBase extends MasterSpringBootTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private ProductController productController;

    @Autowired
    private ResourceBundleMessageSource starterMessageSource;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler(starterMessageSource))
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain));
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        getProductById_should_be_success();
        getProductById_should_be_failed();
        createProduct_should_be_success();
    }

    private void getProductById_should_be_success() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setProductName("Test product name 1");
        productDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        productDto.setCreatedBy(1L);
        productDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        productDto.setUpdatedBy(1L);
        productDto.setDeleted(false);
        Mockito.when(productService.findById(1L)).thenReturn(productDto);
    }

    private void getProductById_should_be_failed() {
        Mockito.when(productService.findById(2L)).thenThrow(new DataNotFoundException("Produk dengan id 2 tidak ada"));
    }

    private void createProduct_should_be_success() {
        String productName = "Test product name new";
        Long totalStock = 25L;

        ProductCreateDto productCreateDto = ProductCreateDto.builder()
                .productName(productName)
                .totalStock(totalStock)
                .build();

        ProductDto productDto = new ProductDto();
        productDto.setId(2L);
        productDto.setProductName(productName);
        productDto.setStatus(ProductStatus.ACTIVE.getValue());
        productDto.setTotalStock(totalStock);
        productDto.setCreatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        productDto.setCreatedBy(1L);
        productDto.setUpdatedAt(LocalDateTime.of(2023, Month.JANUARY, 30, 15, 30, 59));
        productDto.setUpdatedBy(1L);
        productDto.setDeleted(false);
        Mockito.when(productService.createProduct(productCreateDto)).thenReturn(productDto);
    }

}
