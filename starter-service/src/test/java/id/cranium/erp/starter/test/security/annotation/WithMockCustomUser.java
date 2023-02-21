package id.cranium.erp.starter.test.security.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;
import id.cranium.erp.starter.test.security.WithMockCustomUserSecurityContextFactory;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    long userId() default 12L;
	String username() default "user-12";
    String[] authorities() default {};
}
