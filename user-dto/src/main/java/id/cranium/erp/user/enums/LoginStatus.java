package id.cranium.erp.user.enums;

import lombok.Getter;

@Getter
public enum LoginStatus {
    
    INVALID(false),
    VALID(true);

    private final boolean value;

    LoginStatus(boolean value) {
        this.value = value;
    }
}
