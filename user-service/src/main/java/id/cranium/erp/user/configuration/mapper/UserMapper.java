package id.cranium.erp.user.configuration.mapper;

import id.cranium.erp.user.entity.User;
import id.cranium.erp.user.dto.UserDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.property.RegexPropertyResolver;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends ConfigurableMapper {

	@Override
	protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
		factoryBuilder.propertyResolverStrategy(new RegexPropertyResolver(
			"get|is|has([\\w]+)",
			"set([\\w]+)",
			true, true));
    }

	@Override
    protected void configure(MapperFactory factory) {
	    factory.classMap(User.class, UserDto.class)
	      .field("id", "id")
	      .field("username", "username")
	      .field("password", "password")
	      .field("firstName", "firstName")
	      .field("middleName", "middleName")
	      .field("lastName", "lastName")
	      .field("email", "email")
	      .field("mobilePhone", "mobilePhone")
		  .field("domain", "domain")
		  .field("status", "status")
          .field("createdAt", "createdAt")
          .field("createdBy", "createdBy")
          .field("updatedAt", "updatedAt")
          .field("updatedBy", "updatedBy")
		  .field("deleted", "deleted")
	      .byDefault()
	      .register();      
	}
}
