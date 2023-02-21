package id.cranium.erp.inventory.inventoryservice;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import id.cranium.erp.inventory.InventorySpringBootTest;

@SqlGroup({
	@Sql(value = "classpath:db/test/inventory/h2_inventory_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
class InventoryServiceApplicationTests extends InventorySpringBootTest {

	@Test
	void contextLoads() {
	}

}
