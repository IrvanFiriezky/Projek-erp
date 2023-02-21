package id.cranium.erp.inventory;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import id.cranium.erp.inventory.inventorydto.InventoryDtoApplication;

import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = InventoryDtoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"spring.profiles.include=starterdto_test,inventorydto_test" })
public class InventoryDtoSpringBootTest {

}
