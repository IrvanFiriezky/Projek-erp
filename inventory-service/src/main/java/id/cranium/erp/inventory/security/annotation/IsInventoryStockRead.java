package id.cranium.erp.inventory.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.access.prepost.PreAuthorize;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ERP_READ') or hasAuthority('INVENTORY_STOCK_READ')")
public @interface IsInventoryStockRead {
    
}
