package id.cranium.erp.user.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import id.cranium.erp.user.UserSpringBootTest;

@SqlGroup({
	//@Sql(value = "classpath:db/test/user/h2_user_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
class UserServiceApplicationTests extends UserSpringBootTest {

	@Test
	void contextLoads() {
	}

}
