package com.powerofwear.common.enums;

public enum TransactionStatus {
    PENDING("Kutilmoqda"),
    COMPLETED("Yakunlangan"),
    CANCELLED("Bekor qilingan"),
    REVERSED("Orqaga qaytarilgan"),
    PARTIAL("Qisman bajarilgan");

    private final String displayName;

    TransactionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}