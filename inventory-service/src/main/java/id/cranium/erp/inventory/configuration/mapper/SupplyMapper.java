package id.cranium.erp.inventory.configuration.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.property.RegexPropertyResolver;
import org.springframework.stereotype.Component;
import id.cranium.erp.inventory.entity.Supply;
import id.cranium.erp.inventory.dto.SupplyDto;

@Component
public class SupplyMapper extends ConfigurableMapper {

	@Override
	protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
		factoryBuilder.propertyResolverStrategy(new RegexPropertyResolver(
			"get|is|has([\\w]+)",
			"set([\\w]+)",
			true, true));
    }

	@Override
    protected void configure(MapperFactory factory) {
	    factory.classMap(Supply.class, SupplyDto.class)
	      .field("id", "id")
	      .field("supplyName", "supplyName")
		  .field("status", "status")
		  .field("stock.totalStock", "totalStock")
          .field("createdAt", "createdAt")
          .field("createdBy", "createdBy")
          .field("updatedAt", "updatedAt")
          .field("updatedBy", "updatedBy")
		  .field("deleted", "deleted")
	      .byDefault()
	      .register();      
	}
}
