package id.cranium.erp.inventory.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import id.cranium.erp.inventory.service.SupplyService;
import jakarta.annotation.PostConstruct;

@Component
public class SupplyServiceUtil {
    
    private static SupplyServiceUtil instance;

    @Autowired
    private SupplyService supplyService;

    @PostConstruct
    public void fillInstance() {
        instance = this;
    }

    public static SupplyService getSupplyService() {
        return instance.supplyService;
    }
}
