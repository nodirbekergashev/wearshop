package com.powerofwear.common.enums;

public enum TransactionType {
    // KIRIMLAR (INCOME) - quantity musbat
    PURCHASE("Xarid", "Kirim"),
    RETURN("Qaytarish", "Kirim"),
    PRODUCTION("Ishlab chiqarish", "Kirim"),
    TRANSFER_IN("Ko'chirish (kirim)", "Kirim"),
    ADJUSTMENT_IN("Tuzatish (+)", "Kirim"),
    CORRECTION_IN("Hisob-kitob (+)", "Kirim"),

    // CHIQIMLAR (OUTGOING) - quantity manfiy
    SALE("Sotish", "Chiqim"),
    DAMAGED("Shikastlangan", "Chiqim"),
    LOST("Yo'qolgan", "Chiqim"),
    TRANSFER_OUT("Ko'chirish (chiqim)", "Chiqim"),
    ADJUSTMENT_OUT("Tuzatish (-)", "Chiqim"),
    CORRECTION_OUT("Hisob-kitob (-)", "Chiqim"),

    // AJRATISHLAR (RESERVATION)
    RESERVATION("Ajratish", "Ajratish"),
    RESERVATION_RELEASE("Ajratishni bekor qilish", "Ajratish");

    private final String displayName;
    private final String category;

    TransactionType(String displayName, String category) {
        this.displayName = displayName;
        this.category = category;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCategory() {
        return category;
    }

    public boolean isIncoming() {
        return this.category.equals("Kirim");
    }

    public boolean isOutgoing() {
        return this.category.equals("Chiqim");
    }

    public boolean isReservation() {
        return this.category.equals("Ajratish");
    }
}
