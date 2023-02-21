package id.cranium.erp.cron.cronservice;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import id.cranium.erp.cron.CronSpringBootTest;

@SqlGroup({
	//@Sql(value = "classpath:db/test/cron/h2_cron_data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
})
public class CronServiceApplicationTests extends CronSpringBootTest {
    
    @Test
	void contextLoads() {
	}

}
