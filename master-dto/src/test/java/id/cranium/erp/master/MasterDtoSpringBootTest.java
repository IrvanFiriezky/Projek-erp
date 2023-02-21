package id.cranium.erp.master;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import id.cranium.erp.master.masterdto.MasterDtoApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MasterDtoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
	properties = {
    	"spring.profiles.include=starterdto_test,masterdto_test"})
public class MasterDtoSpringBootTest {
    
}
