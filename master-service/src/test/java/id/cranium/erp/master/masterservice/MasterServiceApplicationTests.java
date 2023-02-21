package id.cranium.erp.master.masterservice;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import id.cranium.erp.master.MasterSpringBootTest;

@SqlGroup({
	@Sql(value = "classpath:db/test/master/h2_master_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
class MasterServiceApplicationTests extends MasterSpringBootTest {

	@Test
	void contextLoads() {
	}

}
