package id.cranium.erp.inventory;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import id.cranium.erp.inventory.inventoryservice.InventoryServiceApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = InventoryServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = {
    	"spring.profiles.include=starter_test,starterdto_test,inventory_test,inventorydto_test"})
public class InventorySpringBootTest {
    
}
