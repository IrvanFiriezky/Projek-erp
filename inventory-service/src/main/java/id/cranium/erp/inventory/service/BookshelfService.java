package id.cranium.erp.inventory.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import id.cranium.erp.inventory.dto.BookshelfDto;
import id.cranium.erp.inventory.entity.Bookshelf;
import id.cranium.erp.starter.exception.DataNotFoundException;
import id.cranium.erp.inventory.repository.primary.BookshelfRepository;
import id.cranium.erp.inventory.configuration.mapper.BookshelfMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookshelfService {

  private final BookshelfRepository bookshelfRepository;
  private final BookshelfMapper bookshelfMapper;

  @Autowired
  private ResourceBundleMessageSource inventoryMessageSource;

  private String INVENTORY_BOOKSHELF_FINDID_NOTFOUND = "inventory.bookshelf.findid.notfound";

  @Transactional(value = "inventoryTransactionManager", readOnly = true)
  public BookshelfDto findById(Long id) throws DataNotFoundException {

    log.info("TESTING: InventoryService - findById");
    Optional<Bookshelf> bookshelf;
    bookshelf = bookshelfRepository.findById(id);
    if (bookshelf.isEmpty()) {
      throw new DataNotFoundException(inventoryMessageSource.getMessage(INVENTORY_BOOKSHELF_FINDID_NOTFOUND,
          new Object[] { id }, LocaleContextHolder.getLocale()));
    }
    return bookshelfMapper.map(bookshelf.get(), BookshelfDto.class);
  }

}