package id.cranium.erp.inventory.validator;

import org.springframework.beans.factory.annotation.Autowired;

import id.cranium.erp.inventory.service.BookshelfService;
import id.cranium.erp.inventory.util.BookshelfServiceUtil;
import id.cranium.erp.inventory.validator.constraint.InventoryBookshelfIsExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InventoryBookshelfExistsValidator implements ConstraintValidator<InventoryBookshelfIsExists, Long> {
  @Autowired
  private BookshelfService bookshelfService;

  @Override
  public void initialize(InventoryBookshelfIsExists constraintAnnotation) {
    this.bookshelfService = BookshelfServiceUtil.getBookshelfService();
  }

  @Override
  public boolean isValid(Long bookshelfId, ConstraintValidatorContext constraintValidatorContext) {
    try {
      bookshelfService.findById(bookshelfId);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
