package id.cranium.erp.master.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.slf4j.MDC;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.master.configuration.mapper.ProductMapper;
import id.cranium.erp.master.entity.Product;
import id.cranium.erp.master.entity.Stock;
import id.cranium.erp.master.enums.ProductStatus;
import id.cranium.erp.master.repository.primary.ProductRepository;
import id.cranium.erp.master.dto.ProductDto;
import id.cranium.erp.master.dto.ProductCreateDto;
import id.cranium.erp.master.dto.ProductUpdateDto;
import id.cranium.erp.master.event.ProductCreateEvent;
import id.cranium.erp.starter.service.StarterUserScopeService;
import id.cranium.erp.starter.security.UserAuthInfo;
import id.cranium.erp.starter.configuration.CorrelationConfiguration;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.exception.DataLockException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final StarterUserScopeService starterUserScopeService;
	private final ApplicationEventPublisher productPublisher;

	@Autowired
	private ResourceBundleMessageSource masterMessageSource;

	private String MASTER_PRODUCT_FINDID_NOTFOUND = "master.product.findid.notfound";

	@Transactional(value = "masterTransactionManager")
	public ProductDto createProduct(ProductCreateDto productCreateDto) throws DataNotFoundException {

		log.info("TESTING: ProductService - createProduct");

		Product product = Product.builder()
				.productName(productCreateDto.getProductName())
				.status(ProductStatus.ACTIVE.getValue())
				.build();

		Stock stock = Stock.builder()
				.totalStock(productCreateDto.getTotalStock())
				.product(product)
				.build();
		// stock = stockRepository.save(stock);

		product.setStock(stock);
		product = productRepository.save(product);

		ProductCreateEvent productCreateEvent = ProductCreateEvent.builder()
				.xUserId(UserAuthInfo.getUserId())
				.xRequestId(MDC.get(CorrelationConfiguration.REQUEST_ID_HEADER_NAME))
				.id(product.getId())
				.productName(product.getProductName())
				.build();
		productPublisher.publishEvent(productCreateEvent);

		return productMapper.map(product, ProductDto.class);

	}

	@Transactional(value = "masterTransactionManager", readOnly = true)
	public ProductDto findById(Long id) throws DataNotFoundException {

		log.info("TESTING: ProductService - findById");

		Optional<Product> product;
		if (starterUserScopeService.getMyScopeValue("MASTER_PRODUCT_OWN").equals(Long.toString(UserAuthInfo.getUserId()))) {
			product = productRepository.findByIdAndCreatedBy(id, UserAuthInfo.getUserId());
		} else {
			product = productRepository.findById(id);
		}

		if (product.isEmpty()) {
			throw new DataNotFoundException(masterMessageSource.getMessage(MASTER_PRODUCT_FINDID_NOTFOUND,
					new Object[] { id }, LocaleContextHolder.getLocale()));
		}
		return productMapper.map(product.get(), ProductDto.class);

	}

	@Transactional(value = "masterTransactionManager")
	public ProductDto updateProduct(ProductUpdateDto productUpdateDto, Long id)
			throws DataNotFoundException, DataLockException, ObjectOptimisticLockingFailureException {

		log.info("TESTING: ProductService - updateProduct");

		Product product;
		if (starterUserScopeService.getMyScopeValue("MASTER_PRODUCT_OWN").equals(Long.toString(UserAuthInfo.getUserId()))) {
			product = productRepository.findWithLockingByIdAndCreatedBy(id, UserAuthInfo.getUserId())
					.map(data -> data).orElseThrow(() -> new DataNotFoundException(masterMessageSource
							.getMessage(MASTER_PRODUCT_FINDID_NOTFOUND, new Object[] { id }, LocaleContextHolder.getLocale())));
		} else {
			product = productRepository.findWithLockingById(id)
					.map(data -> data).orElseThrow(() -> new DataNotFoundException(masterMessageSource
							.getMessage(MASTER_PRODUCT_FINDID_NOTFOUND, new Object[] { id }, LocaleContextHolder.getLocale())));
		}
		if (product.getVersion() != productUpdateDto.getVersion()) {
			throw new DataLockException(
					"Database version: " + product.getVersion() + ", Current version: " + productUpdateDto.getVersion());
		}
		product.setProductName(productUpdateDto.getProductName());
		product = productRepository.save(product);

		return productMapper.map(product, ProductDto.class);

	}
}
