package id.cranium.erp.inventory.validator.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Documented
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface InventorySupplyIsExists {
    
    String message() default "{inventory.supply.notexists}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
