package id.cranium.erp.user.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.user.configuration.mapper.UserScopeMapper;
import id.cranium.erp.user.configuration.mapper.UserScopeMeMapper;
import id.cranium.erp.user.entity.UserScope;
import id.cranium.erp.user.entity.custom.UserScopeMe;
import id.cranium.erp.user.repository.primary.UserScopeRepository;
import id.cranium.erp.user.repository.primary.ScopeRepository;
import id.cranium.erp.user.repository.primary.UserRepository;
import id.cranium.erp.user.dto.UserScopeDto;
import id.cranium.erp.user.dto.UserScopeCreateDto;
import id.cranium.erp.user.dto.UserScopeMeDto;
import id.cranium.erp.starter.security.UserAuthInfo;
import id.cranium.erp.starter.exception.DataNotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserScopeService {

    private final UserScopeRepository userScopeRepository;
    private final ScopeRepository scopeRepository;
	private final UserRepository userRepository;
    private final UserScopeMapper userScopeMapper;
    private final UserScopeMeMapper userScopeMeMapper;

	@Autowired
    private ResourceBundleMessageSource userMessageSource;

	private String USER_SCOPE_FINDID_NOTFOUND = "user.scope.findid.notfound";

    @Transactional(value="userTransactionManager", readOnly=true)
	public UserScopeDto findById(Long id) throws DataNotFoundException {
 
		log.info("TESTING: UserScopeService - findById");

		Optional<UserScope> userScope = userScopeRepository.findById(id);
		
		if (userScope.isEmpty()) {
			throw new DataNotFoundException(userMessageSource.getMessage(USER_SCOPE_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale()));
		}
		return userScopeMapper.map(userScope.get(), UserScopeDto.class);
 
	}

    @Transactional(value="userTransactionManager", readOnly=true)
	public Page<UserScopeDto> findByUserId(Long userId, Pageable pageable) throws DataNotFoundException {
 
		log.info("TESTING: UserScopeService - findByDeleted");

		Optional<Page<UserScope>> userScopes = userScopeRepository.findByUserId(userId, pageable);
		
		if (userScopes.isPresent()) {
			return userScopes.get().map(userScope -> userScopeMapper.map(userScope, UserScopeDto.class));
		} else {
			throw new DataNotFoundException(userMessageSource.getMessage(USER_SCOPE_FINDID_NOTFOUND, new Object[]{userId}, LocaleContextHolder.getLocale()));
		}
 
	}

	@Transactional(value="userTransactionManager")
	public UserScopeDto createUserScope(UserScopeCreateDto userScopeCreateDto, Long userId) throws DataNotFoundException {
 
		log.info("TESTING: UserScopeService - createUserScope");

		UserScope userScope = userScopeMapper.map(userScopeCreateDto, UserScope.class);
		userScope.setUser(userRepository.getReferenceById(userId));
        userScope.setScope(scopeRepository.getReferenceById(userScopeCreateDto.getScopeId()));

		userScope = userScopeRepository.save(userScope);
		
		return userScopeMapper.map(userScope, UserScopeDto.class);
 
	}

    @Transactional(value="userTransactionManager", readOnly=true)
	public List<UserScopeMeDto> findMyScope() throws DataNotFoundException {
 
		log.info("TESTING: UserScopeService - findMyScope");

		Optional<List<UserScopeMe>> userScopes = userScopeRepository.findScopeByUserId(UserAuthInfo.getUserId());
		
		if (userScopes.isEmpty()) {
			throw new DataNotFoundException(userMessageSource.getMessage(USER_SCOPE_FINDID_NOTFOUND, new Object[]{UserAuthInfo.getUserId()}, LocaleContextHolder.getLocale()));
		}

        return userScopes.get().stream().map(userScope -> userScopeMeMapper.map(userScope, UserScopeMeDto.class)).collect(Collectors.toList());
 
	}

    @Transactional(value="userTransactionManager", readOnly=true)
	public UserScopeMeDto findMyScopeByName(String scopeName) throws DataNotFoundException {
 
		log.info("TESTING: UserScopeService - findMyScopeByName");

		Optional<UserScopeMe> userScopeHas = userScopeRepository.findMyScopeByName(UserAuthInfo.getUserId(), scopeName);
		
		if (userScopeHas.isEmpty()) {
			throw new DataNotFoundException(userMessageSource.getMessage(USER_SCOPE_FINDID_NOTFOUND, new Object[]{UserAuthInfo.getUserId()}, LocaleContextHolder.getLocale()));
		}

        return userScopeMeMapper.map(userScopeHas.get(), UserScopeMeDto.class);
 
	}
    
}
