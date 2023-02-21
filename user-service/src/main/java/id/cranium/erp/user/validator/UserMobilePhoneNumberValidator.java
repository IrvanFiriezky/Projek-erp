package id.cranium.erp.user.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import id.cranium.erp.user.validator.constraint.UserMobilePhoneNumber;

public class UserMobilePhoneNumberValidator implements ConstraintValidator<UserMobilePhoneNumber, String> {
    
    List<String> allowed;

    @Override
    public void initialize(UserMobilePhoneNumber constraintAnnotation) {
        this.allowed = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        if (!allowed.contains(value.toLowerCase())) {
            String err = "{user.mobilephone.allowed} " + allowed;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(err).addConstraintViolation();
            return false;
        }
        return true;
    }
}
