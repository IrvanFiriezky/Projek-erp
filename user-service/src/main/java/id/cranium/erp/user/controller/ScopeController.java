package id.cranium.erp.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.configuration.aspect.LogExecutionTime;
import id.cranium.erp.user.service.ScopeService;
import id.cranium.erp.user.service.UserScopeService;
import id.cranium.erp.user.dto.ScopeDto;
import id.cranium.erp.user.dto.ScopeCreateDto;
import id.cranium.erp.user.dto.UserScopeMeDto;
import id.cranium.erp.user.security.annotation.IsUserScopeRead;

@RestController
public class ScopeController extends UserServiceController {
    
    @Autowired
    private ScopeService scopeService;

    @Autowired
    private UserScopeService userScopeService;

    @PostMapping(value = "/scope", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogExecutionTime
    public ScopeDto createScope(@Validated @RequestBody ScopeCreateDto scopeCreateDto) throws DataNotFoundException {
        return scopeService.createScope(scopeCreateDto);
    }

    @GetMapping(value = "/scope/{id}", headers = "X-Api-Version=1")
    @IsUserScopeRead
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public ScopeDto getScopeById(@PathVariable long id) throws DataNotFoundException {
        return scopeService.findById(id);
    }

    @GetMapping(value = "/scope/me", headers = "X-Api-Version=1")
    @IsUserScopeRead
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public List<UserScopeMeDto> getScopeByUserId() throws DataNotFoundException {
        return userScopeService.findMyScope();
    }

    @GetMapping(value = "/scope/me/{name}", headers = "X-Api-Version=1")
    @IsUserScopeRead
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public UserScopeMeDto getMyScopeByName(@PathVariable String name) throws DataNotFoundException {
        return userScopeService.findMyScopeByName(name);
    }
}
