package id.cranium.erp.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import id.cranium.erp.inventory.dto.BookshelfDto;
import id.cranium.erp.inventory.service.BookshelfService;
import id.cranium.erp.starter.configuration.aspect.LogExecutionTime;
import id.cranium.erp.starter.exception.DataNotFoundException;

@RestController
public class BookshelfController extends InventoryServiceController {

  @Autowired
  private BookshelfService bookshelfService;

  @GetMapping(value = "/bookshelf/{id}", headers = "X-Api-Version=1")
  @ResponseStatus(value = HttpStatus.OK)
  @LogExecutionTime
  public BookshelfDto getBookshelfById(@PathVariable long id) throws DataNotFoundException {
    return bookshelfService.findById(id);
  }
}
