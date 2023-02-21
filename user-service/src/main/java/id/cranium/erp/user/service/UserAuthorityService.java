package id.cranium.erp.user.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import id.cranium.erp.user.configuration.mapper.UserAuthorityMapper;
import id.cranium.erp.user.entity.UserAuthority;
import id.cranium.erp.user.repository.primary.UserAuthorityRepository;
import id.cranium.erp.user.repository.primary.AuthorityRepository;
import id.cranium.erp.user.repository.primary.UserRepository;
import id.cranium.erp.user.dto.UserAuthorityDto;
import id.cranium.erp.user.dto.UserAuthorityCreateDto;
import id.cranium.erp.starter.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAuthorityService {
    
    private final UserAuthorityRepository userAuthorityRepository;
    private final AuthorityRepository authorityRepository;
	private final UserRepository userRepository;
    private final UserAuthorityMapper userAuthorityMapper;

	@Autowired
    private ResourceBundleMessageSource userMessageSource;

	private String USER_AUTHORITY_FINDID_NOTFOUND = "user.authority.findid.notfound";

    @Transactional(value="userTransactionManager", readOnly=true)
	public UserAuthorityDto findById(Long id) throws DataNotFoundException {
 
		log.info("TESTING: UserAuthorityService - findById");

		Optional<UserAuthority> userAuthority = userAuthorityRepository.findById(id);
		
		if (userAuthority.isEmpty()) {
			throw new DataNotFoundException(userMessageSource.getMessage(USER_AUTHORITY_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale()));
		}
		return userAuthorityMapper.map(userAuthority.get(), UserAuthorityDto.class);
 
	}

    @Transactional(value="userTransactionManager", readOnly=true)
	public Page<UserAuthorityDto> findByUserId(Long userId, Pageable pageable) throws DataNotFoundException {
 
		log.info("TESTING: UserAuthorityService - findByDeleted");

		Optional<Page<UserAuthority>> userAuthorities = userAuthorityRepository.findByUserId(userId, pageable);
		
		if (userAuthorities.isPresent()) {
			return userAuthorities.get().map(userAuthority -> userAuthorityMapper.map(userAuthority, UserAuthorityDto.class));
		} else {
			throw new DataNotFoundException(userMessageSource.getMessage(USER_AUTHORITY_FINDID_NOTFOUND, new Object[]{userId}, LocaleContextHolder.getLocale()));
		}
 
	}

	@Transactional(value="userTransactionManager")
	public UserAuthorityDto createUserAuthority(UserAuthorityCreateDto userAuthorityCreateDto, Long userId) throws DataNotFoundException {
 
		log.info("TESTING: UserAuthorityService - createUserAuthority");

		UserAuthority userAuthority = userAuthorityMapper.map(userAuthorityCreateDto, UserAuthority.class);
		userAuthority.setUser(userRepository.getReferenceById(userId));
        userAuthority.setAuthority(authorityRepository.getReferenceById(userAuthorityCreateDto.getAuthorityId()));

		userAuthority = userAuthorityRepository.save(userAuthority);
		
		return userAuthorityMapper.map(userAuthority, UserAuthorityDto.class);
 
	}
}
