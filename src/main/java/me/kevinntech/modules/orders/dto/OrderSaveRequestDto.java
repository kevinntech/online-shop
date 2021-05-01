package me.kevinntech.modules.orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kevinntech.modules.products.dto.ProductToOrderForm;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderSaveRequestDto {

    private List<ProductToOrderForm> productToOrderForms = new ArrayList<>();

    private String deliveredName; // 받는 사람 이름

    private String address; // 주소

    private String phoneNumber; // 전화번호

    private String RequestsForDelivery; // 배송 시 요청사항

}
