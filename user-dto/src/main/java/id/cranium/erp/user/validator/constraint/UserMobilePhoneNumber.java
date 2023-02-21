package id.cranium.erp.user.validator.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Documented
@NotNull(message = "{user.mobilephone.notnull}")
@NotEmpty(message = "{user.mobilephone.notempty}")
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface UserMobilePhoneNumber {
    
    String message() default "{user.mobilephone.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] allowed() default {"08111111111", "08122222222", "08133333333"};
}
