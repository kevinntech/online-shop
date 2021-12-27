package me.kevinntech.modules.orders.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kevinntech.modules.orders.enums.DeliveryStatus;
import me.kevinntech.modules.orders.enums.OrderStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderDetailsDto {

    private Long id;

    private String productCode;

    private String productName;

    private Long orderPrice;

    private Long orderQuantity;

    private Long orderAmount;

    private OrderStatus orderStatus; // 주문 상태 [ORDER, CANCEL]

    private DeliveryStatus deliveryStatus; // 배송 상태

    private String orderedName; // 주문한 사람 이름

    private String deliveredName; // 받는 사람 이름

    private String address; // 주소

    private String phoneNumber; // 전화번호

    private LocalDateTime orderedAt;

    private Long displayOrderDetailId;

    @QueryProjection
    public OrderDetailsDto(Long id, String productCode, String productName, Long orderPrice, Long orderQuantity, Long orderAmount, OrderStatus orderStatus, DeliveryStatus deliveryStatus, String orderedName, String deliveredName, String address, String phoneNumber, LocalDateTime orderedAt) {
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
        this.orderedName = orderedName;
        this.deliveredName = deliveredName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orderedAt = orderedAt;
    }

}
