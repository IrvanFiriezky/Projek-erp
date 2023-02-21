package id.cranium.erp.master.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import id.cranium.erp.master.service.ProductService;
import jakarta.annotation.PostConstruct;

@Component
public class ProductServiceUtil {
    
    private static ProductServiceUtil instance;

    @Autowired
    private ProductService productService;

    @PostConstruct
    public void fillInstance() {
        instance = this;
    }

    public static ProductService getProductService() {
        return instance.productService;
    }
}
