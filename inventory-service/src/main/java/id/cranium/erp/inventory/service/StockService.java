package id.cranium.erp.inventory.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.PessimisticLockingFailureException;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import id.cranium.erp.inventory.configuration.mapper.StockMapper;
import id.cranium.erp.inventory.configuration.mapper.SupplyStockMapper;
import id.cranium.erp.inventory.entity.Stock;
import id.cranium.erp.inventory.entity.custom.SupplyStock;
import id.cranium.erp.inventory.repository.primary.StockRepository;
import id.cranium.erp.inventory.dto.StockDto;
import id.cranium.erp.inventory.dto.StockUpdateDto;
import id.cranium.erp.inventory.dto.SupplyStockDto;
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
    private final SupplyStockMapper supplyStockMapper;
    private final StarterUserScopeService starterUserScopeService;

	@Autowired
    private ResourceBundleMessageSource inventoryMessageSource;

	private String INVENTORY_STOCK_FINDID_NOTFOUND = "inventory.stock.findid.notfound";

	@Transactional(value="inventoryTransactionManager", readOnly=true)
	public StockDto findById(Long id) throws DataNotFoundException {

		log.info("TESTING: StockService - findById");
 
        Optional<Stock> stock;
        if (starterUserScopeService.getMyScopeValue("INVENTORY_STOCK_OWN").equals(Long.toString(UserAuthInfo.getUserId()))) {
		    stock = stockRepository.findByIdAndCreatedBy(id, UserAuthInfo.getUserId());
        } else {
            stock = stockRepository.findById(id);
        }
		
		if (stock.isEmpty()) {
			throw new DataNotFoundException(inventoryMessageSource.getMessage(INVENTORY_STOCK_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale()));
		}
		return stockMapper.map(stock.get(), StockDto.class);
 
	}

	@Transactional(value="inventoryTransactionManager")
	public StockDto updateStock(StockUpdateDto stockUpdateDto, Long id) throws DataNotFoundException, DataLockException {
 
		log.info("TESTING: StockService - updateStock");
        
        try {
            Optional<Stock> optionalStock;
            if (starterUserScopeService.getMyScopeValue("INVENTORY_STOCK_OWN").equals(Long.toString(UserAuthInfo.getUserId()))) {
                optionalStock = stockRepository.findWithLockingByIdAndCreatedBy(id, UserAuthInfo.getUserId());
            } else {
                optionalStock = stockRepository.findWithLockingById(id);
            }

            if (optionalStock.isEmpty()) {
                throw new DataNotFoundException(inventoryMessageSource.getMessage(INVENTORY_STOCK_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale()));
            }

            Stock stock = optionalStock.get();
            stock.setTotalStock(stockUpdateDto.getTotalStock());
            stock = stockRepository.save(stock);

            return stockMapper.map(stock, StockDto.class);
        } catch(PessimisticLockingFailureException ex) {
            throw new DataLockException(ex.getMessage());
        }
	}

    @Transactional(value="inventoryTransactionManager", readOnly=true)
	public SupplyStockDto findStockSupplyById(Long id) throws DataNotFoundException {

		log.info("TESTING: StockService - findStockSupplyById");
 
        Optional<SupplyStock> supplyStock = stockRepository.findStockSupplyById(id);
		
		if (supplyStock.isEmpty()) {
			throw new DataNotFoundException(inventoryMessageSource.getMessage(INVENTORY_STOCK_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale()));
		}
		return supplyStockMapper.map(supplyStock.get(), SupplyStockDto.class);
 
	}
}
