package id.cranium.erp.master.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.configuration.aspect.LogExecutionTime;
import id.cranium.erp.master.service.ProductService;
import id.cranium.erp.master.dto.ProductDto;
import id.cranium.erp.master.dto.ProductCreateDto;
import id.cranium.erp.master.security.annotation.IsMasterProductRead;

@RestController
public class ProductController extends MasterServiceController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/product", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogExecutionTime
    public ProductDto createProduct(@Validated @RequestBody ProductCreateDto productCreateDto) throws DataNotFoundException {
        return productService.createProduct(productCreateDto);
    }

    @GetMapping(value = "/product/{id}", headers = "X-Api-Version=1")
    @IsMasterProductRead
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public ProductDto getProductById(@PathVariable long id) throws DataNotFoundException {
        return productService.findById(id);
    }
}
