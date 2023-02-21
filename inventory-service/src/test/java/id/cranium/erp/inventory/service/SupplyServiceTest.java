package id.cranium.erp.inventory.service;

import org.junit.jupiter.api.Assertions;
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
import id.cranium.erp.inventory.dto.SupplyDto;
import id.cranium.erp.inventory.dto.SupplyCreateDto;
import id.cranium.erp.inventory.dto.SupplyUpdateDto;
import id.cranium.erp.inventory.event.SupplyCreateEvent;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.service.StarterUserScopeService;
import id.cranium.erp.starter.exception.DataLockException;
import id.cranium.erp.inventory.InventorySpringBootTest;

@RecordApplicationEvents
public class SupplyServiceTest extends InventorySpringBootTest {

    @Autowired
    private ApplicationEvents applicationEvents;

    @Autowired
    private SupplyService supplyService;

    @MockBean
    private StarterUserScopeService starterUserScopeService;

    @BeforeEach
    public void setup() {
        Mockito.when(starterUserScopeService.getMyScopeValue("INVENTORY_SUPPLY_OWN")).thenReturn("");
    }

    @Test
    void testCreateSupply() throws DataNotFoundException {
        String supplyName = "Test Create Supply";
        Long totalStock = 15L;

        SupplyCreateDto supplyCreateDto = SupplyCreateDto.builder()
            .supplyName(supplyName)
            .totalStock(totalStock)
            .build();

        SupplyDto supplyDto = supplyService.createSupply(supplyCreateDto);
        assertEquals(supplyDto.getSupplyName(), supplyName);
        assertEquals(supplyDto.getTotalStock(), totalStock);

        assertEquals(1, applicationEvents
            .stream(SupplyCreateEvent.class)
            .filter(event -> event.getSupplyName().equals(supplyName))
            .count());
    }

    @Test
    void testUpdateSupply() throws DataNotFoundException, DataLockException, ObjectOptimisticLockingFailureException {
        Long supplyId = 1L;
        String supplyName = "Test Update Supply";

        SupplyUpdateDto supplyUpdateDto = SupplyUpdateDto.builder()
            .supplyName(supplyName)
            .version(1L)
            .build();

        SupplyDto supplyDto = supplyService.updateSupply(supplyUpdateDto, supplyId);
        assertEquals(supplyDto.getSupplyName(), supplyName);
    }

    @Test
    void testUpdateSupplyThrowException() throws DataNotFoundException, DataLockException, ObjectOptimisticLockingFailureException {
        Long supplyId = 2L;
        String supplyName = "Test Update Supply";

        SupplyUpdateDto supplyUpdateDto = SupplyUpdateDto.builder()
            .supplyName(supplyName)
            .version(2L)
            .build();
        
        Exception exception = assertThrows(DataLockException.class, () -> {
            SupplyDto supplyDto = supplyService.updateSupply(supplyUpdateDto, supplyId);
        });
        
        String expectedMessage = "Database version: 1, Current version: 2";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testfindById() throws DataNotFoundException {
        Long supplyId = 2L;
        String supplyNameExpected = "Supply 02";

        SupplyDto supplyDto = supplyService.findById(supplyId);
        assertEquals(supplyDto.getSupplyName(), supplyNameExpected);
    }

    @Test
    void testingByIdThrowException() throws DataNotFoundException {
        Long supplyId = 10L;
        
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            SupplyDto supplyDto = supplyService.findById(supplyId);
        });
        
        String expectedMessage = "Supply dengan id 10 tidak ada";
        String actualMessage = exception.getMessage();
    
        Assertions.assertFalse(actualMessage.contains(expectedMessage));
    }
}
