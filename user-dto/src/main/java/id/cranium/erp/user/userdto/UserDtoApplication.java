package id.cranium.erp.user.userdto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"id.cranium.erp"})
public class UserDtoApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(UserDtoApplication.class, args);
	}

}
