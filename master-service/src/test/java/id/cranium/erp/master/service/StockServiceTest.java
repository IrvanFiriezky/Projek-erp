package id.cranium.erp.master.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import id.cranium.erp.master.dto.StockDto;
import id.cranium.erp.master.dto.StockUpdateDto;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.service.StarterUserScopeService;
import id.cranium.erp.starter.exception.DataLockException;
import id.cranium.erp.master.MasterSpringBootTest;

public class StockServiceTest extends MasterSpringBootTest {
    
    @Autowired
    private StockService stockService;

    @MockBean
    private StarterUserScopeService starterUserScopeService;

    @BeforeEach
    public void setup() {
        Mockito.when(starterUserScopeService.getMyScopeValue("MASTER_STOCK_OWN")).thenReturn("");
    }

    @Test
    void testUpdateStock() throws DataNotFoundException, DataLockException {
        Long stockId = 1L;
        Long totalStock = 25L;

        StockUpdateDto stockUpdateDto = StockUpdateDto.builder()
            .totalStock(totalStock)
            .build();

        StockDto stockDto = stockService.updateStock(stockUpdateDto, stockId);
        assertEquals(stockDto.getTotalStock(), totalStock);
    }

    @Test
    void testfindById() throws DataNotFoundException {
        Long stockId = 2L;
        Long totalStockExpected = 20L;

        StockDto stockDto = stockService.findById(stockId);
        assertEquals(stockDto.getTotalStock(), totalStockExpected);
    }

    @Test
    void testfindByIdThrowException() throws DataNotFoundException {
        Long stockId = 10L;
        
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            StockDto stockDto = stockService.findById(stockId);
        });
        
        String expectedMessage = "Stok dengan id 10 tidak ada";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
