package id.cranium.erp.inventory.inventorydto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "id.cranium.erp" })
public class InventoryDtoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryDtoApplication.class, args);
	}

}
