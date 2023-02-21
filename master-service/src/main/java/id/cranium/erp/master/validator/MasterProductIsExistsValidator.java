package id.cranium.erp.master.validator;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import id.cranium.erp.master.service.ProductService;
import id.cranium.erp.master.util.ProductServiceUtil;
import id.cranium.erp.master.validator.constraint.MasterProductIsExists;
import id.cranium.erp.starter.exception.DataNotFoundException;

public class MasterProductIsExistsValidator implements ConstraintValidator<MasterProductIsExists, Long> {

    @Autowired
    private ProductService productService;

    @Override
    public void initialize(MasterProductIsExists constraintAnnotation) {
        this.productService = ProductServiceUtil.getProductService();
    }

    @Override
    public boolean isValid(Long productId, ConstraintValidatorContext constraintValidatorContext) {
        try {
            productService.findById(productId);
            return true;
        } catch (DataNotFoundException e) {
            return false;
        }
    }
}
