package id.cranium.erp.master.masterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"id.cranium.erp"})
public class MasterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterServiceApplication.class, args);
	}

}
