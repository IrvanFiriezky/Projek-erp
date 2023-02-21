package id.cranium.erp.inventory.validator;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import id.cranium.erp.inventory.service.SupplyService;
import id.cranium.erp.inventory.util.SupplyServiceUtil;
import id.cranium.erp.inventory.validator.constraint.InventorySupplyIsExists;
import id.cranium.erp.starter.exception.DataNotFoundException;

public class InventorySupplyIsExistsValidator implements ConstraintValidator<InventorySupplyIsExists, Long> {
    
    @Autowired
    private SupplyService supplyService;

    @Override
    public void initialize(InventorySupplyIsExists constraintAnnotation) {
        this.supplyService = SupplyServiceUtil.getSupplyService();
    }

    @Override
    public boolean isValid(Long supplyId, ConstraintValidatorContext constraintValidatorContext) {
        try {
            supplyService.findById(supplyId);
            return true;
        }catch (DataNotFoundException e){
            return false;
        }
    }
}
