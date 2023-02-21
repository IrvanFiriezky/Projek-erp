package id.cranium.erp.master.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import id.cranium.erp.master.dto.ProductDto;
import id.cranium.erp.master.dto.ProductCreateDto;
import id.cranium.erp.master.dto.ProductUpdateDto;
import id.cranium.erp.master.event.ProductCreateEvent;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.service.StarterUserScopeService;
import id.cranium.erp.starter.exception.DataLockException;
import id.cranium.erp.master.MasterSpringBootTest;

@RecordApplicationEvents
public class ProductServiceTest extends MasterSpringBootTest {

    @Autowired
    private ApplicationEvents applicationEvents;

    @Autowired
    private ProductService productService;

    @MockBean
    private StarterUserScopeService starterUserScopeService;

    @BeforeEach
    public void setup() {
        Mockito.when(starterUserScopeService.getMyScopeValue("MASTER_PRODUCT_OWN")).thenReturn("");
    }

    @Test
    void testCreateProduct() throws DataNotFoundException {
        String productName = "Test Create Product";
        Long totalStock = 15L;

        ProductCreateDto productCreateDto = ProductCreateDto.builder()
                .productName(productName)
                .totalStock(totalStock)
                .build();

        ProductDto productDto = productService.createProduct(productCreateDto);
        assertEquals(productDto.getProductName(), productName);
        assertEquals(productDto.getTotalStock(), totalStock);

        assertEquals(1, applicationEvents
                .stream(ProductCreateEvent.class)
                .filter(event -> event.getProductName().equals(productName))
                .count());
    }

    @Test
    void testUpdateProduct() throws DataNotFoundException, DataLockException, ObjectOptimisticLockingFailureException {
        Long productId = 1L;
        String productName = "Test Update Product";

        ProductUpdateDto productUpdateDto = ProductUpdateDto.builder()
                .productName(productName)
                .version(1L)
                .build();

        ProductDto productDto = productService.updateProduct(productUpdateDto, productId);
        assertEquals(productDto.getProductName(), productName);
    }

    @Test
    void testUpdateProductThrowException()
            throws DataNotFoundException, DataLockException, ObjectOptimisticLockingFailureException {
        Long productId = 2L;
        String productName = "Test Update Product";

        ProductUpdateDto productUpdateDto = ProductUpdateDto.builder()
                .productName(productName)
                .version(2L)
                .build();

        Exception exception = assertThrows(DataLockException.class, () -> {
            ProductDto productDto = productService.updateProduct(productUpdateDto, productId);
        });

        String expectedMessage = "Database version: 1, Current version: 2";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testfindById() throws DataNotFoundException {
        Long productId = 2L;
        String productNameExpected = "Product 02";

        ProductDto productDto = productService.findById(productId);
        assertEquals(productDto.getProductName(), productNameExpected);
    }

    @Test
    void testfindByIdThrowException() throws DataNotFoundException {
        Long productId = 10L;

        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            ProductDto productDto = productService.findById(productId);
        });

        String expectedMessage = "Produk dengan id 10 tidak ada";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
