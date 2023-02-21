package id.cranium.erp.user.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import id.cranium.erp.user.configuration.mapper.AuthorityMapper;
import id.cranium.erp.user.entity.Authority;
import id.cranium.erp.user.repository.primary.AuthorityRepository;
import id.cranium.erp.user.dto.AuthorityDto;
import id.cranium.erp.user.dto.AuthorityCreateDto;
import id.cranium.erp.starter.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorityService {
    
    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

	@Autowired
    private ResourceBundleMessageSource userMessageSource;

	private String USER_AUTHORITY_FINDID_NOTFOUND = "user.authority.findid.notfound";

    @Transactional(value="userTransactionManager", readOnly=true)
	public AuthorityDto findById(Long id) throws DataNotFoundException {
 
		log.info("TESTING: AuthorityService - findById");

		Optional<Authority> authority = authorityRepository.findById(id);
		
		if (authority.isEmpty()) {
			throw new DataNotFoundException(userMessageSource.getMessage(USER_AUTHORITY_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale()));
		}
		return authorityMapper.map(authority.get(), AuthorityDto.class);
 
	}

	@Transactional(value="userTransactionManager")
	public AuthorityDto createAuthority(AuthorityCreateDto authorityCreateDto) throws DataNotFoundException {
 
		log.info("TESTING: AuthorityService - createAuthority");

		Authority authority = authorityMapper.map(authorityCreateDto, Authority.class);

		authority = authorityRepository.save(authority);
		
		return authorityMapper.map(authority, AuthorityDto.class);
 
	}
}
