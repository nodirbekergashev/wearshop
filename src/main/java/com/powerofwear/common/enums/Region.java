package com.powerofwear.common.enums;

public enum Region {
    TASHKENT("Toshkent shahri"),
    TASHKENT_REGION("Toshkent viloyati"),
    ANDIJAN("Andijon viloyati"),
    BUKHARA("Buxoro viloyati"),
    FERGANA("Farg'ona viloyati"),
    JIZZAKH("Jizzax viloyati"),
    NAMANGAN("Namangan viloyati"),
    NAVOI("Navoiy viloyati"),
    KASHKADARYA("Qashqadaryo viloyati"),
    SAMARKAND("Samarqand viloyati"),
    SURKHANDARYA("Surxondaryo viloyati"),
    SIRDARYA("Sirdaryo viloyati"),
    KHOREZM("Xorazm viloyati");

    private final String displayName;

    Region(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}