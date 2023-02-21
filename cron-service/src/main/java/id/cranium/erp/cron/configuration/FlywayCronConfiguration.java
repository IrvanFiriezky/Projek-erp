package id.cranium.erp.cron.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.flywaydb.core.Flyway;

@Configuration
public class FlywayCronConfiguration {
    
    @Autowired
    @Qualifier("cronFlyway")
    private Flyway flyWay;

    @PostConstruct
    public void migrate() {
        //flyWay.clean();
        flyWay.migrate();
    }
}