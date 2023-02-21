package id.cranium.erp.user.configuration.mapper;

import id.cranium.erp.user.entity.UserAuthority;
import id.cranium.erp.user.dto.UserAuthorityDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.property.RegexPropertyResolver;
import org.springframework.stereotype.Component;

@Component
public class UserAuthorityMapper extends ConfigurableMapper {
    
    @Override
	protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
		factoryBuilder.propertyResolverStrategy(new RegexPropertyResolver(
			"get|is|has([\\w]+)",
			"set([\\w]+)",
			true, true));
    }

	@Override
    protected void configure(MapperFactory factory) {
	    factory.classMap(UserAuthority.class, UserAuthorityDto.class)
	      .field("id", "id")
		  .field("user.id", "userId")
          .field("authority.id", "authorityId")
	      .field("authority.authorityName", "authorityName")
          .field("createdAt", "createdAt")
          .field("createdBy", "createdBy")
          .field("updatedAt", "updatedAt")
          .field("updatedBy", "updatedBy")
		  .field("deleted", "deleted")
	      .byDefault()
	      .register();      
	}
}
