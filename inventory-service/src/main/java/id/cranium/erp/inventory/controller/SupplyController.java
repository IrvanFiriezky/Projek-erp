package id.cranium.erp.inventory.controller;

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
import id.cranium.erp.inventory.service.SupplyService;
import id.cranium.erp.inventory.dto.SupplyDto;
import id.cranium.erp.inventory.dto.SupplyCreateDto;
import id.cranium.erp.inventory.security.annotation.IsInventorySupplyRead;

@RestController
public class SupplyController extends InventoryServiceController {

    @Autowired
    private SupplyService supplyService;

    @PostMapping(value = "/supply", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogExecutionTime
    public SupplyDto createSupply(@Validated @RequestBody SupplyCreateDto supplyCreateDto) throws DataNotFoundException {
        return supplyService.createSupply(supplyCreateDto);
    }

    @GetMapping(value = "/supply/{id}", headers = "X-Api-Version=1")
    @IsInventorySupplyRead
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public SupplyDto getSupplyById(@PathVariable long id) throws DataNotFoundException {
        return supplyService.findById(id);
    }
}
