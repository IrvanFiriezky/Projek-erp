package id.cranium.erp.user.enums;

import lombok.Getter;

@Getter
public enum UserStatus {

    INACTIVE(0),
    ACTIVE(1),
    CONFIRM(2);

    private final int value;

    UserStatus(int value) {
        this.value = value;
    }
}
