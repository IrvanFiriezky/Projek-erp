package id.cranium.erp.web.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;


@SpringBootApplication(scanBasePackages = {"id.cranium.erp"})
//@SpringBootApplication(scanBasePackages = {"id.cranium.erp"}, exclude = { SecurityAutoConfiguration.class })
//@SpringBootApplication(scanBasePackages = {"id.cranium.erp"})
//@EnableAutoConfiguration(exclude = {ValidationAutoConfiguration.class})
//@EnableAsync
public class WebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServiceApplication.class, args);
	}

}
