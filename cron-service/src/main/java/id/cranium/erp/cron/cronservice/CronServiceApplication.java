package id.cranium.erp.cron.cronservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"id.cranium.erp"})
public class CronServiceApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(CronServiceApplication.class, args);
	}

}
