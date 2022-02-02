package me.kevinntech.modules.orders.enums;

public enum CodeType {
    ORDER_NUMBER("주문번호"), PRODUCT_CODE("상품코드");

    private final String value;

    CodeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
