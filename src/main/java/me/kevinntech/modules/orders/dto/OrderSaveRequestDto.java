package me.kevinntech.modules.orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kevinntech.modules.products.dto.ProductToOrderForm;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderSaveRequestDto {

    private List<ProductToOrderForm> productToOrderForms = new ArrayList<>();

    @NotBlank
    private String deliveredName; // 받는 사람 이름

    @NotBlank
    private String address; // 주소

    @NotBlank
    private String phoneNumber; // 전화번호

    private String RequestsForDelivery; // 배송 시 요청사항

}
