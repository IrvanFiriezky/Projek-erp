package id.cranium.erp.starter;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import id.cranium.erp.starter.starterservice.StarterServiceApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StarterServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
	properties = {
    	"spring.profiles.include=starter_test,starterdto_test"})
public class StarterSpringBootTest {
    
}
