package id.cranium.erp.user;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest;
import id.cranium.erp.user.userdto.UserDtoApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserDtoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
	properties = {
    	"spring.profiles.include=starterdto_test,userdto_test"})
public class UserDtoSpringBootTest {
    
}
