package id.cranium.erp.inventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.exception.DataLockException;
import id.cranium.erp.starter.configuration.aspect.LogExecutionTime;
import id.cranium.erp.inventory.service.StockService;
import id.cranium.erp.inventory.dto.StockDto;
import id.cranium.erp.inventory.dto.StockUpdateDto;
import id.cranium.erp.inventory.security.annotation.IsInventoryStockRead;

@RestController
public class StockController extends InventoryServiceController {

    @Autowired
    private StockService stockService;

    @GetMapping(value = "/stock/{id}", headers = "X-Api-Version=1")
    @IsInventoryStockRead
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public StockDto getStockById(@PathVariable long id) throws DataNotFoundException {
        return stockService.findById(id);
    }

    @PatchMapping(value = "/stock/{id}", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public StockDto updateStock(@Validated @RequestBody StockUpdateDto stockUpdateDto, @PathVariable long id) throws DataNotFoundException, DataLockException {
        return stockService.updateStock(stockUpdateDto, id);
    }
    
}
