package id.cranium.erp.user.configuration.mapper;

import id.cranium.erp.user.entity.UserScope;
import id.cranium.erp.user.dto.UserScopeDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.property.RegexPropertyResolver;
import org.springframework.stereotype.Component;

@Component
public class UserScopeMapper extends ConfigurableMapper {
    
    @Override
	protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
		factoryBuilder.propertyResolverStrategy(new RegexPropertyResolver(
			"get|is|has([\\w]+)",
			"set([\\w]+)",
			true, true));
    }

	@Override
    protected void configure(MapperFactory factory) {
	    factory.classMap(UserScope.class, UserScopeDto.class)
	      .field("id", "id")
		  .field("user.id", "userId")
          .field("scope.id", "scopeId")
	      .field("scope.scopeName", "scopeName")
          .field("scopeValue", "scopeValue")
          .field("createdAt", "createdAt")
          .field("createdBy", "createdBy")
          .field("updatedAt", "updatedAt")
          .field("updatedBy", "updatedBy")
		  .field("deleted", "deleted")
	      .byDefault()
	      .register();      
	}
}

