package id.cranium.erp.inventory.configuration.mapper;

import org.springframework.stereotype.Component;

import id.cranium.erp.inventory.dto.BookshelfDto;
import id.cranium.erp.inventory.entity.Bookshelf;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.property.RegexPropertyResolver;

@Component
public class BookshelfMapper extends ConfigurableMapper {
  @Override
  protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
    factoryBuilder.propertyResolverStrategy(new RegexPropertyResolver(
        "get|is|has([\\w]+)",
        "set([\\w]+)",
        true, true));
  }

  protected void configure(MapperFactory factory) {
    factory.classMap(Bookshelf.class, BookshelfDto.class)
        .field("id", "id")
        .field("bookshelfName", "bookshelfName")
        .field("status", "status")
        .field("book.bookName", "bookName")
        .field("createdAt", "createdAt")
        .field("createdBy", "createdBy")
        .field("updatedAt", "updatedAt")
        .field("updatedBy", "updatedBy")
        .field("deleted", "deleted")
        .byDefault()
        .register();
  }
}
