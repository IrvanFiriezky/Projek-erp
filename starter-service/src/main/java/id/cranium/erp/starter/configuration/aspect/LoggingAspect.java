package id.cranium.erp.starter.configuration.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Around("@annotation(LogExecutionTime)")
	public Object methodTimeLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		StopWatch stopWatch = new StopWatch(className + "->" + methodName);
		stopWatch.start(methodName);
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();
        log.info(proceedingJoinPoint.getSignature() + " executed in " + stopWatch.getTotalTimeMillis() + "ms");
		return result;
	}
}
