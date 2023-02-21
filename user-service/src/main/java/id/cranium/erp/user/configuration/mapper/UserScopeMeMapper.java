package id.cranium.erp.user.configuration.mapper;

import id.cranium.erp.user.entity.custom.UserScopeMe;
import id.cranium.erp.user.dto.UserScopeMeDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.property.RegexPropertyResolver;
import org.springframework.stereotype.Component;

@Component
public class UserScopeMeMapper extends ConfigurableMapper {
    
    @Override
	protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
		factoryBuilder.propertyResolverStrategy(new RegexPropertyResolver(
			"get|is|has([\\w]+)",
			"set([\\w]+)",
			true, true));
    }

	@Override
    protected void configure(MapperFactory factory) {
	    factory.classMap(UserScopeMe.class, UserScopeMeDto.class)
	      .field("scopeName", "scopeName")
          .field("scopeValue", "scopeValue")
	      .byDefault()
	      .register();      
	}
}
