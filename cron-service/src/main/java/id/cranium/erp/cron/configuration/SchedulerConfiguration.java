package id.cranium.erp.cron.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfiguration {
    
    @Value("${cron.spring.task.scheduling.pool.size}")
    private String cronSpringSchedulingPoolSize;

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(Integer.parseInt(cronSpringSchedulingPoolSize));
        threadPoolTaskScheduler.setThreadNamePrefix("scheduler-app-");
        return threadPoolTaskScheduler;
    }
}
