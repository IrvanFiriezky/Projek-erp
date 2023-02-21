package id.cranium.erp.starter.starterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"id.cranium.erp"})
public class StarterServiceApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(StarterServiceApplication.class, args);
	}

}
