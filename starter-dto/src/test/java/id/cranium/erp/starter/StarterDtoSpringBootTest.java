package id.cranium.erp.starter;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import id.cranium.erp.starter.starterdto.StarterDtoApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StarterDtoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
	properties = {
    	"spring.profiles.include=starterdto_test"})
public class StarterDtoSpringBootTest {
    
}
