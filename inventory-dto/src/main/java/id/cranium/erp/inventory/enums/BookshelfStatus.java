package id.cranium.erp.inventory.enums;

import lombok.Getter;

@Getter
public enum BookshelfStatus {

  FULL(0),
  AVAILABLE(1),
  BROKEN(2),
  FIXING(3),
  EXPIRED(4);

  private final int value;

  BookshelfStatus(int value) {
    this.value = value;
  }

}
