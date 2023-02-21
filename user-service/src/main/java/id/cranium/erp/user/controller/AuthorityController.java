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
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.configuration.aspect.LogExecutionTime;
import id.cranium.erp.user.service.AuthorityService;
import id.cranium.erp.user.dto.AuthorityDto;
import id.cranium.erp.user.dto.AuthorityCreateDto;
import id.cranium.erp.user.security.annotation.IsUserAuthorityRead;

@RestController
public class AuthorityController extends UserServiceController {
    
    @Autowired
    private AuthorityService authorityService;

    @PostMapping(value = "/authority", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogExecutionTime
    public AuthorityDto createAuthority(@Validated @RequestBody AuthorityCreateDto authorityCreateDto) throws DataNotFoundException {
        return authorityService.createAuthority(authorityCreateDto);
    }

    @GetMapping(value = "/authority/{id}", headers = "X-Api-Version=1")
    @IsUserAuthorityRead
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public AuthorityDto getAuthorityById(@PathVariable long id) throws DataNotFoundException {
        return authorityService.findById(id);
    }
}
