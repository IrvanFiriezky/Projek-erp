package id.cranium.erp.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import id.cranium.erp.user.entity.User;
import id.cranium.erp.starter.entity.JwtUserDetails;
import id.cranium.erp.starter.dto.UserAuthInfoDto;
import id.cranium.erp.auth.repository.primary.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    
	private final UserDetailsRepository userDetailsRepository;

	@Autowired
    private ResourceBundleMessageSource authMessageSource;

	private String AUTH_LOGIN_FAILED = "auth.login.failed";

    @Override
	@Transactional(value="authTransactionManager", readOnly=true)
	public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 
		log.info("TESTING: JwtUserDetailsService - loadUserByUsername " + username + " - " + LocaleContextHolder.getLocale());

		Optional<User> userEntity = userDetailsRepository.findByUsername(username);
		Optional<org.springframework.security.core.userdetails.User> userCore = Optional.empty();

		if (!userEntity.isEmpty()) {
			List<GrantedAuthority> authorities = userEntity.get().getUserAuthority().stream().map(userAuthority -> 
				new SimpleGrantedAuthority(userAuthority.getAuthority().getAuthorityName()))
				.collect(Collectors.toList());

			log.info("TESTING: JwtUserDetailsService - loadUserByUsername " + authorities);

			userCore = Optional.of(new org.springframework.security.core.userdetails.User(userEntity.get().getUsername(), userEntity.get().getPassword(), authorities));
		}

		return userCore.map(user -> new JwtUserDetails(user, UserAuthInfoDto.builder().userId(userEntity.get().getId()).username(userEntity.get().getUsername()).build())).orElseThrow(() -> new UsernameNotFoundException(authMessageSource.getMessage(AUTH_LOGIN_FAILED, null, LocaleContextHolder.getLocale())));
 
	}

	@Transactional(value="authTransactionManager", readOnly=true)
	public JwtUserDetails loadUserByUsernameAndDomain(String username, String domain) throws UsernameNotFoundException {
 
		log.info("TESTING: JwtUserDetailsService - loadUserByUsernameAndDomain");

		Optional<User> userEntity = userDetailsRepository.findByUsernameAndDomain(username, domain);
		Optional<org.springframework.security.core.userdetails.User> userCore = Optional.empty();

		if (!userEntity.isEmpty()) {
			List<GrantedAuthority> authorities = userEntity.get().getUserAuthority().stream().map(userAuthority -> 
				new SimpleGrantedAuthority(userAuthority.getAuthority().getAuthorityName()))
				.collect(Collectors.toList());

			userCore = Optional.of(new org.springframework.security.core.userdetails.User(userEntity.get().getUsername(), userEntity.get().getPassword(), authorities));
		
		}
		
		return userCore.map(user -> new JwtUserDetails(user, UserAuthInfoDto.builder().userId(userEntity.get().getId()).username(userEntity.get().getUsername()).build())).orElseThrow(() -> new UsernameNotFoundException(authMessageSource.getMessage(AUTH_LOGIN_FAILED, null, LocaleContextHolder.getLocale())));
 
	}

}
