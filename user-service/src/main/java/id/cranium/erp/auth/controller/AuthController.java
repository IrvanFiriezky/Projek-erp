package id.cranium.erp.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import id.cranium.erp.starter.exception.DataNotFoundException;
//import id.cranium.erp.starter.configuration.aspect.LogExecutionTime;
import id.cranium.erp.user.dto.LoginDto;
import id.cranium.erp.auth.service.LoginService;

@RestController
public class AuthController extends AuthServiceController {
    
    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/verification/token/{accessToken}", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.OK)
    //@LogExecutionTime
    public LoginDto getLoginByAccessToken(@PathVariable String accessToken) throws DataNotFoundException {
        return loginService.findByAccessTokenStatusDeleted(accessToken);
    }

    @GetMapping(value = "/history/username/{username}", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.OK)
    //@LogExecutionTime
    public Page<LoginDto> getByUsername(@PathVariable String username, Pageable pageable) throws DataNotFoundException {
        return loginService.findByUsername(username, pageable);
    }

    @GetMapping(value = "/login/expired", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.OK)
    //@LogExecutionTime
    public Page<LoginDto> getExpiredLogin(Pageable pageable) throws DataNotFoundException {
        return loginService.findExpiredLogin(pageable);
    }
}
