package id.cranium.erp.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.user.configuration.mapper.LoginMapper;
import id.cranium.erp.user.entity.Login;
import id.cranium.erp.user.dto.LoginDto;
import id.cranium.erp.auth.repository.primary.LoginRepository;
import id.cranium.erp.auth.repository.slave.LoginSlaveRepository;
import id.cranium.erp.starter.exception.DataNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    
    private final LoginRepository loginRepository;
	private final LoginSlaveRepository loginSlaveRepository;
    private final LoginMapper loginMapper;

	@Autowired
    private ResourceBundleMessageSource authMessageSource;

	private String USER_LOGIN_FIND_NOTFOUND = "auth.login.notfound";

	@Transactional(value="authTransactionManager", readOnly=true)
	public LoginDto findByAccessToken(String accessToken) throws DataNotFoundException {
 
		log.info("TESTING: LoginService - findByAccessToken " + accessToken);

		Optional<Login> login = loginRepository.findByAccessToken(accessToken);
		
		if (login.isEmpty()) {
			throw new DataNotFoundException(authMessageSource.getMessage(USER_LOGIN_FIND_NOTFOUND, new Object[]{accessToken}, LocaleContextHolder.getLocale()));
		}

		return loginMapper.map(login.get(), LoginDto.class);
 
	}

    @Transactional(value="authTransactionManager", readOnly=true)
	public LoginDto findByAccessTokenStatusDeleted(String accessToken) throws DataNotFoundException {
 
		//log.info("TESTING: LoginService - findByAccessTokenStatusDeleted " + accessToken);

		Optional<Login> login = loginRepository.findByAccessTokenAndStatusAndDeleted(accessToken, true, false);

		if (login.isEmpty()) {
			throw new DataNotFoundException(authMessageSource.getMessage(USER_LOGIN_FIND_NOTFOUND, new Object[]{accessToken}, LocaleContextHolder.getLocale()));
		}

		return loginMapper.map(login.get(), LoginDto.class);
 
	}

    @Transactional(value="authTransactionManager", readOnly=true)
	public Page<LoginDto> findByUsername(String username, Pageable pageable) throws DataNotFoundException {
 
		log.info("TESTING: LoginService - findByUserId " + username);

		Optional<Page<Login>> logins = loginSlaveRepository.findByUsername(username, pageable);

        if (logins.isPresent()) {
			return logins.get().map(login -> loginMapper.map(login, LoginDto.class));
		} else {
			throw new DataNotFoundException(authMessageSource.getMessage(USER_LOGIN_FIND_NOTFOUND, null, LocaleContextHolder.getLocale()));
		}

	}

	@Transactional(value="authTransactionManager", readOnly=true)
	public Page<LoginDto> findExpiredLogin(Pageable pageable) throws DataNotFoundException {
 
		log.info("TESTING: LoginService - findExpiredLogin ");

		Optional<Page<Login>> logins = loginSlaveRepository.findByCreatedAtGreaterThan(LocalDateTime.now(), pageable);

        if (logins.isPresent()) {
			return logins.get().map(login -> loginMapper.map(login, LoginDto.class));
		} else {
			throw new DataNotFoundException(authMessageSource.getMessage(USER_LOGIN_FIND_NOTFOUND, null, LocaleContextHolder.getLocale()));
		}

	}

    @Transactional(value="authTransactionManager")
	public LoginDto createLogin(LoginDto loginDto) throws DataNotFoundException {
 
		log.info("TESTING: LoginService - createLogin");

		//Login login = loginMapper.map(loginDto, Login.class);
        Login login = Login.builder()
            .accessToken(loginDto.getAccessToken())
            .username(loginDto.getUsername())
            .userId(loginDto.getUserId())
            .status(loginDto.isStatus())
            .build();

		login = loginRepository.save(login);
		
		return loginMapper.map(login, LoginDto.class);
 
	}

}
