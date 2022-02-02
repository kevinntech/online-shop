package me.kevinntech.modules.orders.enums;

public enum OrderStatus {
    ORDER("주문"), CANCEL("취소");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
