package id.cranium.erp.master.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.PessimisticLockingFailureException;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.master.configuration.mapper.StockMapper;
import id.cranium.erp.master.configuration.mapper.ProductStockMapper;
import id.cranium.erp.master.entity.Stock;
import id.cranium.erp.master.entity.custom.ProductStock;
import id.cranium.erp.master.repository.primary.StockRepository;
import id.cranium.erp.master.dto.StockDto;
import id.cranium.erp.master.dto.StockUpdateDto;
import id.cranium.erp.master.dto.ProductStockDto;
import id.cranium.erp.starter.service.StarterUserScopeService;
import id.cranium.erp.starter.security.UserAuthInfo;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.exception.DataLockException;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockService {
    
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final ProductStockMapper productStockMapper;
    private final StarterUserScopeService starterUserScopeService;

	@Autowired
    private ResourceBundleMessageSource masterMessageSource;

	private String MASTER_STOCK_FINDID_NOTFOUND = "master.stock.findid.notfound";

	@Transactional(value="masterTransactionManager", readOnly=true)
	public StockDto findById(Long id) throws DataNotFoundException {

		log.info("TESTING: StockService - findById");
 
        Optional<Stock> stock;
        if (starterUserScopeService.getMyScopeValue("MASTER_STOCK_OWN").equals(Long.toString(UserAuthInfo.getUserId()))) {
		    stock = stockRepository.findByIdAndCreatedBy(id, UserAuthInfo.getUserId());
        } else {
            stock = stockRepository.findById(id);
        }
		
		if (stock.isEmpty()) {
			throw new DataNotFoundException(masterMessageSource.getMessage(MASTER_STOCK_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale()));
		}
		return stockMapper.map(stock.get(), StockDto.class);
 
	}

	@Transactional(value="masterTransactionManager")
	public StockDto updateStock(StockUpdateDto stockUpdateDto, Long id) throws DataNotFoundException, DataLockException {
 
		log.info("TESTING: StockService - updateStock");
        
        try {
            Optional<Stock> optionalStock;
            if (starterUserScopeService.getMyScopeValue("MASTER_STOCK_OWN").equals(Long.toString(UserAuthInfo.getUserId()))) {
                optionalStock = stockRepository.findWithLockingByIdAndCreatedBy(id, UserAuthInfo.getUserId());
            } else {
                optionalStock = stockRepository.findWithLockingById(id);
            }

            if (optionalStock.isEmpty()) {
                throw new DataNotFoundException(masterMessageSource.getMessage(MASTER_STOCK_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale()));
            }

            Stock stock = optionalStock.get();
            stock.setTotalStock(stockUpdateDto.getTotalStock());
            stock = stockRepository.save(stock);

            return stockMapper.map(stock, StockDto.class);
        } catch(PessimisticLockingFailureException ex) {
            throw new DataLockException(ex.getMessage());
        }
	}

    @Transactional(value="masterTransactionManager", readOnly=true)
	public ProductStockDto findStockProductById(Long id) throws DataNotFoundException {

		log.info("TESTING: StockService - findStockProductById");
 
        Optional<ProductStock> productStock = stockRepository.findStockProductById(id);
		
		if (productStock.isEmpty()) {
			throw new DataNotFoundException(masterMessageSource.getMessage(MASTER_STOCK_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale()));
		}
		return productStockMapper.map(productStock.get(), ProductStockDto.class);
 
	}
}
