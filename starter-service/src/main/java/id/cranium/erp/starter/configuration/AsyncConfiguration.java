package id.cranium.erp.starter.configuration;

import java.util.concurrent.Executor;
import java.util.Map;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {

    @Value("${starter.async.config.corepoolsize}")
    private String corePoolSize;
    
    @Value("${starter.async.config.maxpoolsize}")
    private String maxPoolSize;

    @Value("${starter.async.config.queuecapacity}")
    private String queueCapacity;
    
    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(Integer.parseInt(corePoolSize));
        threadPoolTaskExecutor.setMaxPoolSize(Integer.parseInt(maxPoolSize));
        threadPoolTaskExecutor.setQueueCapacity(Integer.parseInt(queueCapacity));
        threadPoolTaskExecutor.setKeepAliveSeconds(30);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolTaskExecutor.setThreadNamePrefix("async-method-");
        threadPoolTaskExecutor.setTaskDecorator(new ContextCopyingTaskDecorator());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(Integer.parseInt(corePoolSize));
        threadPoolTaskExecutor.setMaxPoolSize(Integer.parseInt(maxPoolSize));
        threadPoolTaskExecutor.setQueueCapacity(Integer.parseInt(queueCapacity));
        threadPoolTaskExecutor.setKeepAliveSeconds(30);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolTaskExecutor.setThreadNamePrefix("async-app-");
        threadPoolTaskExecutor.setTaskDecorator(new ContextCopyingTaskDecorator());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    private final class ContextCopyingTaskDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable task) {
            Map<String, String> contextMap = MDC.getCopyOfContextMap();

            if (RequestContextHolder.getRequestAttributes() != null) {
                RequestAttributes context = RequestContextHolder.currentRequestAttributes();

                return () -> {
                    try {
                        RequestContextHolder.setRequestAttributes(context);
                        MDC.setContextMap(contextMap);
                        task.run();
                    } finally {
                        MDC.clear();
                        RequestContextHolder.resetRequestAttributes();
                    }
                };
            } else {
                return () -> {
                    try {
                        MDC.setContextMap(contextMap);
                        task.run();
                    } finally {
                        MDC.clear();
                    }
                };
            }

            
            
            
        }
    }
}
