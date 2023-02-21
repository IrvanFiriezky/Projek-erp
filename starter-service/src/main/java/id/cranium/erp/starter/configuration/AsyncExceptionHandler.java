package id.cranium.erp.starter.configuration;

import java.lang.reflect.Method;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    
    @Override
    public void handleUncaughtException(final Throwable throwable, final Method method, final Object... obj) {
        log.error("Exception message - " + throwable.getMessage());
        log.info("Method name - " + method.getName());
        for (final Object param : obj) {
            log.info("Param - " + param);
        }
    }
}
