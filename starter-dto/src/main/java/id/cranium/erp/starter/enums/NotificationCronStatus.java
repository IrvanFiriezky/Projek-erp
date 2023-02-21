package id.cranium.erp.starter.enums;

import lombok.Getter;

@Getter
public enum NotificationCronStatus {

    SUCCESS("SUCCESS"),
    FAILED("FAILED");

    private final String value;

    NotificationCronStatus(String value) {
        this.value = value;
    }
}
