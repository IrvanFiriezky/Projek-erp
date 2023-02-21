package id.cranium.erp.cron;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import id.cranium.erp.cron.cronservice.CronServiceApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CronServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
	properties = {
    	"spring.profiles.include=starter_test,starterdto_test,cron_test,userdto_test,masterdto_test"})
public class CronSpringBootTest {
    
}
