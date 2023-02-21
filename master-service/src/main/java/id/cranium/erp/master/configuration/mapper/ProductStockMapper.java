package id.cranium.erp.master.configuration.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.property.RegexPropertyResolver;
import org.springframework.stereotype.Component;
import id.cranium.erp.master.entity.custom.ProductStock;
import id.cranium.erp.master.dto.ProductStockDto;

@Component
public class ProductStockMapper extends ConfigurableMapper {
    
    @Override
	protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
		factoryBuilder.propertyResolverStrategy(new RegexPropertyResolver(
			"get|is|has([\\w]+)",
			"set([\\w]+)",
			true, true));
    }

	@Override
    protected void configure(MapperFactory factory) {
	    factory.classMap(ProductStock.class, ProductStockDto.class)
	      .field("productName", "productName")
		  .field("totalStock", "totalStock")
	      .byDefault()
	      .register();      
	}
}
