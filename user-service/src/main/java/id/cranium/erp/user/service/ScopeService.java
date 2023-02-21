package id.cranium.erp.user.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import id.cranium.erp.user.configuration.mapper.ScopeMapper;
import id.cranium.erp.user.entity.Scope;
import id.cranium.erp.user.repository.primary.ScopeRepository;
import id.cranium.erp.user.dto.ScopeDto;
import id.cranium.erp.user.dto.ScopeCreateDto;
import id.cranium.erp.starter.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScopeService {
    
    private final ScopeRepository scopeRepository;
    private final ScopeMapper scopeMapper;

	@Autowired
    private ResourceBundleMessageSource userMessageSource;

	private String USER_SCOPE_FINDID_NOTFOUND = "user.scope.findid.notfound";

    @Transactional(value="userTransactionManager", readOnly=true)
	public ScopeDto findById(Long id) throws DataNotFoundException {
 
		log.info("TESTING: ScopeService - findById");

		Optional<Scope> scope = scopeRepository.findById(id);
		
		if (scope.isEmpty()) {
			throw new DataNotFoundException(userMessageSource.getMessage(USER_SCOPE_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale()));
		}
		return scopeMapper.map(scope.get(), ScopeDto.class);
 
	}

	@Transactional(value="userTransactionManager")
	public ScopeDto createScope(ScopeCreateDto scopeCreateDto) throws DataNotFoundException {
 
		log.info("TESTING: ScopeService - createScope");

		Scope scope = scopeMapper.map(scopeCreateDto, Scope.class);

		scope = scopeRepository.save(scope);
		
		return scopeMapper.map(scope, ScopeDto.class);
 
	}
}
