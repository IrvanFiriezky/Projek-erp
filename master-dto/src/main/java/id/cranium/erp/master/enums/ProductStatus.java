package id.cranium.erp.master.enums;

import lombok.Getter;

@Getter
public enum ProductStatus {

    INACTIVE(0),
    ACTIVE(1),
    EXPIRED(2);

    private final int value;

    ProductStatus(int value) {
        this.value = value;
    }
}
