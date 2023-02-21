package id.cranium.erp.starter.starterdto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"id.cranium.erp"})
public class StarterDtoApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(StarterDtoApplication.class, args);
	}

}
