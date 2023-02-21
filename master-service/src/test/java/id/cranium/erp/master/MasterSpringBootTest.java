package id.cranium.erp.master;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import id.cranium.erp.master.masterservice.MasterServiceApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MasterServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
		"spring.profiles.include=starter_test,starterdto_test,master_test,masterdto_test" })
public class MasterSpringBootTest {

}
