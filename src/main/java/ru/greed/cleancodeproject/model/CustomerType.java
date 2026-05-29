package ru.greed.cleancodeproject.model;

import org.apache.commons.lang3.EnumUtils;

public enum CustomerType {

    VIP(0.90),
    NEW(0.95),
    REGULAR(1.0);

    private final double discountRate;

    CustomerType(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public static CustomerType fromString(String type) {
        return EnumUtils.getEnumIgnoreCase(CustomerType.class, type, REGULAR);
    }
}
