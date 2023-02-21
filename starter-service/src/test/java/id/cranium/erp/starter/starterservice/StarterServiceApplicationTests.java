package id.cranium.erp.starter.starterservice;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import id.cranium.erp.starter.StarterSpringBootTest;

@SqlGroup({
	//@Sql(value = "classpath:db/test/starter/h2_starter_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
public class StarterServiceApplicationTests extends StarterSpringBootTest {
    
    @Test
	void contextLoads() {
	}

}
