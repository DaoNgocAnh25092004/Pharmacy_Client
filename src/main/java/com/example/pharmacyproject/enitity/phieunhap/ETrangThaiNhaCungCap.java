package com.example.pharmacyproject.enitity.phieunhap;

public enum ETrangThaiNhaCungCap {
    DANG_LAM_VIEC("Đang làm việc"),
    NGUNG_NHAP_HANG("Ngừng nhập hàng");

    private final String displayName;

    // Constructor to initialize displayName
    ETrangThaiNhaCungCap(String displayName) {
        this.displayName = displayName;
    }

    // Getter method to retrieve display name
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    // Method to get enum by display name
    public static ETrangThaiNhaCungCap fromDisplayName(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            throw new IllegalArgumentException("Display name cannot be null or empty");
        }
        // Use valueOf() to improve performance and reduce the need for looping
        for (ETrangThaiNhaCungCap trangThaiNhaCungCap : ETrangThaiNhaCungCap.values()) {
            if (trangThaiNhaCungCap.getDisplayName().equalsIgnoreCase(displayName)) {
                return trangThaiNhaCungCap;
            }
        }
        throw new IllegalArgumentException("No enum constant with display name " + displayName);
    }
}
