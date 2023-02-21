package id.cranium.erp.user;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import id.cranium.erp.user.userservice.UserServiceApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
	properties = {
    	"spring.profiles.include=starter_test,starterdto_test,user_test,userdto_test,masterdto_test"})
public class UserSpringBootTest {
    
}
