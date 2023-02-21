package id.cranium.erp.inventory.service;

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
import id.cranium.erp.inventory.configuration.mapper.SupplyMapper;
import id.cranium.erp.inventory.entity.Supply;
import id.cranium.erp.inventory.entity.Stock;
import id.cranium.erp.inventory.enums.SupplyStatus;
import id.cranium.erp.inventory.repository.primary.SupplyRepository;
import id.cranium.erp.inventory.dto.SupplyDto;
import id.cranium.erp.inventory.dto.SupplyCreateDto;
import id.cranium.erp.inventory.dto.StockUpdateDto;
import id.cranium.erp.inventory.event.SupplyCreateEvent;
import id.cranium.erp.starter.service.StarterUserScopeService;
import id.cranium.erp.starter.security.UserAuthInfo;
import id.cranium.erp.starter.configuration.CorrelationConfiguration;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.starter.exception.DataLockException;

import static id.cranium.erp.inventory.event.SupplyCreateEvent.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final SupplyMapper supplyMapper;
	private final StarterUserScopeService starterUserScopeService;
	private final ApplicationEventPublisher supplyPublisher;

	@Autowired
    private ResourceBundleMessageSource inventoryMessageSource;

	private String INVENTORY_SUPPLY_FINDID_NOTFOUND = "inventory.supply.findid.notfound";

	@Transactional(value="inventoryTransactionManager")
	public SupplyDto createSupply(SupplyCreateDto supplyCreateDto) throws DataNotFoundException {
 
		log.info("TESTING: SupplyService - createSupply");

		Supply supply = Supply.builder()
			.supplyName(supplyCreateDto.getSupplyName())
			.status(SupplyStatus.ACTIVE.getValue())
			.build();
		
		Stock stock = Stock.builder()
			.totalStock(supplyCreateDto.getTotalStock())
			.supply(supply)
			.build();
		//stock = stockRepository.save(stock);

		supply.setStock(stock);
		supply = supplyRepository.save(supply);

		SupplyCreateEvent supplyCreateEvent = SupplyCreateEvent.builder()
			.xUserId(UserAuthInfo.getUserId())
			.xRequestId(MDC.get(CorrelationConfiguration.REQUEST_ID_HEADER_NAME))
			.id(supply.getId())
			.supplyname(supply.getSupplyName())
			.build();
		supplyPublisher.publishEvent(supplyCreateEvent);
		
		return supplyMapper.map(supply, SupplyDto.class);
 
	}

	@Transactional(value="inventoryTransactionManager", readOnly=true)
	public SupplyDto findById(Long id) throws DataNotFoundException {

		log.info("TESTING: SupplyService - findById");
 
		Optional<Supply> supply;
		if (starterUserScopeService.getMyScopeValue("INVENTORY_SUPPLY_OWN").equals(Long.toString(UserAuthInfo.getUserId()))) {
			supply = supplyRepository.findByIdAndCreatedBy(id, UserAuthInfo.getUserId());
		} else {
			supply = supplyRepository.findById(id);
		}
		
		if (supply.isEmpty()) {
			throw new DataNotFoundException(inventoryMessageSource.getMessage(INVENTORY_SUPPLY_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale()));
		}
		return supplyMapper.map(supply.get(), SupplyDto.class);
 
	}

	@Transactional(value="inventoryTransactionManager")
	public SupplyDto updateSupply(StockUpdateDto stockUpdateDto, Long id) throws DataNotFoundException, DataLockException, ObjectOptimisticLockingFailureException {
 
		log.info("TESTING: SupplyService - updateSupply");

		Supply supply;
		if (starterUserScopeService.getMyScopeValue("INVENTORY_SUPPLY_OWN").equals(Long.toString(UserAuthInfo.getUserId()))) {
			supply = supplyRepository.findWithLockingByIdAndCreatedBy(id, UserAuthInfo.getUserId())
				.map(data -> data).orElseThrow(() -> new DataNotFoundException(inventoryMessageSource.getMessage(INVENTORY_SUPPLY_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale())));
		} else {
			supply = supplyRepository.findWithLockingById(id)
				.map(data -> data).orElseThrow(() -> new DataNotFoundException(inventoryMessageSource.getMessage(INVENTORY_SUPPLY_FINDID_NOTFOUND, new Object[]{id}, LocaleContextHolder.getLocale())));
		}
		if (supply.getVersion() != stockUpdateDto.getVersion()) {
			throw new DataLockException("Database version: " + supply.getVersion() + ", Current version: " + stockUpdateDto.getVersion());
		}
		supply.setSupplyName(stockUpdateDto.getSupplyName());
		supply = supplyRepository.save(supply);

		return supplyMapper.map(supply, SupplyDto.class);
 
	}
}

