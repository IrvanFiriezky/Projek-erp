package id.cranium.erp.user.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import id.cranium.erp.user.configuration.mapper.UserMapper;
import id.cranium.erp.user.entity.User;
import id.cranium.erp.user.repository.primary.UserRepository;
import id.cranium.erp.user.dto.UserDto;
import id.cranium.erp.user.dto.UserRequestDto;
import id.cranium.erp.user.dto.UserCreateDto;
import id.cranium.erp.starter.exception.ServerException;
import id.cranium.erp.starter.exception.ClientException;
import id.cranium.erp.starter.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import id.cranium.erp.master.dto.ProductDto;
import id.cranium.erp.user.client.master.UserProductWebClient;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
	private final UserProductWebClient productWebClient;

	@Autowired
    private ResourceBundleMessageSource userMessageSource;

	private String USER_USER_FINDID_NOTFOUND = "user.user.findid.notfound";
	private String USER_USER_FINDDELETED_NOTFOUND = "user.user.finddeleted.notfound";

	@Transactional(value="userTransactionManager", readOnly=true)
	public UserDto findById(Long id) throws DataNotFoundException {
 
		log.info("TESTING: UserService - findById");

		Optional<User> user = userRepository.findById(id);
		
		if (user.isEmpty()) {
			throw new DataNotFoundException(userMessageSource.getMessage(USER_USER_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale()));
		}
		return userMapper.map(user.get(), UserDto.class);
 
	}

	@Transactional(value="userTransactionManager", readOnly=true)
	public Page<UserDto> findByDeleted(boolean deleted, Pageable pageable) throws DataNotFoundException {
 
		log.info("TESTING: UserService - findByDeleted");

		Optional<Page<User>> users = userRepository.findByDeleted(deleted, pageable);
		
		if (users.isPresent()) {
			return users.get().map(user -> userMapper.map(user, UserDto.class));
		} else {
			throw new DataNotFoundException(userMessageSource.getMessage(USER_USER_FINDDELETED_NOTFOUND, null, LocaleContextHolder.getLocale()));
		}
 
	}

	@Transactional(value="userTransactionManager")
	public UserDto updateUserProfile(UserRequestDto userRequestDto, Long id) throws DataNotFoundException {
 
		log.info("TESTING: UserService - updateUserProfile");

		User user = userRepository.findById(id).map(data -> data).orElseThrow(() -> new DataNotFoundException(userMessageSource.getMessage(USER_USER_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale())));
		
		user.setMobilePhone(userRequestDto.getMobilePhone());
		user = userRepository.save(user);
		
		return userMapper.map(user, UserDto.class);
 
	}

	@Transactional(value="userTransactionManager")
	public UserDto createUserProfile(UserCreateDto userCreateDto) throws DataNotFoundException {
 
		log.info("TESTING: UserService - createUserProfile");
		
		userCreateDto.setPassword(new BCryptPasswordEncoder().encode(userCreateDto.getPassword()));
		User user = userMapper.map(userCreateDto, User.class);

		user = userRepository.save(user);
		
		return userMapper.map(user, UserDto.class);
 
	}

	public ProductDto getProductById(Long id) throws ServerException, ClientException {
		log.info("TESTING: UserService - getProductById");
		return productWebClient.getProductById(id);
	}
}
