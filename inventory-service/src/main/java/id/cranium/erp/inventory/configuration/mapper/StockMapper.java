package id.cranium.erp.inventory.configuration.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.property.RegexPropertyResolver;
import org.springframework.stereotype.Component;
import id.cranium.erp.inventory.entity.Stock;
import id.cranium.erp.inventory.dto.StockDto;

@Component
public class StockMapper extends ConfigurableMapper {
    
    @Override
	protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
		factoryBuilder.propertyResolverStrategy(new RegexPropertyResolver(
			"get|is|has([\\w]+)",
			"set([\\w]+)",
			true, true));
    }

	@Override
    protected void configure(MapperFactory factory) {
	    factory.classMap(Stock.class, StockDto.class)
	      .field("id", "id")
	      .field("supply.id", "supplyId")
		  .field("totalStock", "totalStock")
          .field("createdAt", "createdAt")
          .field("createdBy", "createdBy")
          .field("updatedAt", "updatedAt")
          .field("updatedBy", "updatedBy")
		  .field("deleted", "deleted")
	      .byDefault()
	      .register();      
	}
}
