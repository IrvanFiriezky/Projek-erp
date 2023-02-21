package id.cranium.erp.inventory.configuration.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.property.RegexPropertyResolver;
import org.springframework.stereotype.Component;
import id.cranium.erp.inventory.entity.custom.SupplyStock;
import id.cranium.erp.inventory.dto.SupplyStockDto;

@Component
public class SupplyStockMapper extends ConfigurableMapper {
    
    @Override
	protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
		factoryBuilder.propertyResolverStrategy(new RegexPropertyResolver(
			"get|is|has([\\w]+)",
			"set([\\w]+)",
			true, true));
    }

	@Override
    protected void configure(MapperFactory factory) {
	    factory.classMap(SupplyStock.class, SupplyStockDto.class)
	      .field("supplyName", "supplyName")
		  .field("totalStock", "totalStock")
	      .byDefault()
	      .register();      
	}
}
