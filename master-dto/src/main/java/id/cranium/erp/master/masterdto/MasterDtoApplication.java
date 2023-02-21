package id.cranium.erp.master.masterdto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"id.cranium.erp"})
public class MasterDtoApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(MasterDtoApplication.class, args);
	}

}
