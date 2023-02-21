package id.cranium.erp.inventory.enums;

import lombok.Getter;

@Getter
public enum SupplyStatus {

    INACTIVE(0),
    ACTIVE(1),
    EXPIRED(2);

    private final int value;

    SupplyStatus(int value) {
        this.value = value;
    }
}
