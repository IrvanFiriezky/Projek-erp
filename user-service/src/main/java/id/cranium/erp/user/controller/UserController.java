package id.cranium.erp.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.configuration.aspect.LogExecutionTime;
import id.cranium.erp.user.service.UserService;
import id.cranium.erp.user.service.UserAuthorityService;
import id.cranium.erp.user.service.UserScopeService;
import id.cranium.erp.user.dto.UserDto;
import id.cranium.erp.user.dto.UserCreateDto;
import id.cranium.erp.user.dto.UserRequestDto;
import id.cranium.erp.user.dto.UserAuthorityDto;
import id.cranium.erp.user.dto.UserAuthorityCreateDto;
import id.cranium.erp.user.dto.UserScopeDto;
import id.cranium.erp.user.dto.UserScopeCreateDto;
import id.cranium.erp.user.validator.constraint.UserMobilePhoneNumber;
import id.cranium.erp.user.security.annotation.IsUserProfileRead;
import id.cranium.erp.master.dto.ProductDto;

@RestController
public class UserController extends UserServiceController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthorityService userAuthorityService;

    @Autowired
    private UserScopeService userScopeService;

    @PostMapping(value = "/user", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogExecutionTime
    public UserDto createUserProfile(@Validated @RequestBody UserCreateDto userCreateDto) throws DataNotFoundException {
        return userService.createUserProfile(userCreateDto);
    }

    @PatchMapping(value = "/user/{id}", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public UserDto updateUser(@Validated @RequestBody UserRequestDto userRequestDto, @PathVariable long id) throws DataNotFoundException {
        return userService.updateUserProfile(userRequestDto, id);
    }

    @GetMapping(value = "/user/{id}", headers = "X-Api-Version=1")
    @IsUserProfileRead
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public UserDto getUserById(@PathVariable long id) throws DataNotFoundException {
        return userService.findById(id);
    }

    @GetMapping(value = "/user/status", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public Page<UserDto> getUserByDeleted(@RequestParam boolean deleted, Pageable pageable) throws DataNotFoundException {
        return userService.findByDeleted(deleted, pageable);
    }

    @GetMapping(value = "/user/product/{id}", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public ProductDto getProductById(@PathVariable long id) throws DataNotFoundException {
        return userService.getProductById(id);
    }

    @GetMapping(value = "/user/{id}/authority", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public Page<UserAuthorityDto> getAuthorityByUserId(@PathVariable long id, Pageable pageable) throws DataNotFoundException {
        return userAuthorityService.findByUserId(id, pageable);
    }

    @PostMapping(value = "/user/{id}/authority", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogExecutionTime
    public UserAuthorityDto createUserAuthority(@RequestBody UserAuthorityCreateDto userAuthorityCreateDto, @PathVariable long id) throws DataNotFoundException {
        return userAuthorityService.createUserAuthority(userAuthorityCreateDto, id);
    }

    @GetMapping(value = "/user/{id}/scope", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.OK)
    @LogExecutionTime
    public Page<UserScopeDto> getScopeByUserId(@PathVariable long id, Pageable pageable) throws DataNotFoundException {
        return userScopeService.findByUserId(id, pageable);
    }

    @PostMapping(value = "/user/{id}/scope", headers = "X-Api-Version=1")
    @ResponseStatus(value = HttpStatus.CREATED)
    @LogExecutionTime
    public UserScopeDto createUserScope(@RequestBody UserScopeCreateDto userScopeCreateDto, @PathVariable long id) throws DataNotFoundException {
        return userScopeService.createUserScope(userScopeCreateDto, id);
    }
}
