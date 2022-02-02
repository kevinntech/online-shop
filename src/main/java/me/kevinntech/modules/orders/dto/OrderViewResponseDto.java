package me.kevinntech.modules.orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kevinntech.modules.orders.Order;
import me.kevinntech.modules.orders.OrderProduct;
import me.kevinntech.modules.orders.enums.DeliveryStatus;
import me.kevinntech.modules.orders.enums.OrderStatus;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderViewResponseDto {

    private Long id;

    private String orderedProductName;

    private long orderedTotalAmount;

    private OrderStatus orderStatus; // 주문 상태 [ORDER, CANCEL]

    private DeliveryStatus deliveryStatus; // 배송 상태

    private String orderedName; // 주문한 사람 이름

    private String deliveredName; // 받는 사람 이름

    private String address; // 주소

    private String phoneNumber; // 전화번호

    private String requestsForDelivery; // 배송 시 요청사항

    public OrderViewResponseDto(Order order) {
        this.id = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.deliveryStatus = order.getDeliveryStatus();
        this.orderedName = order.getOrderedName();
        this.deliveredName = order.getDeliveredName();
        this.address = order.getAddress();
        this.phoneNumber = order.getPhoneNumber();
        this.requestsForDelivery = order.getRequestsForDelivery();

        List<OrderProduct> orderProducts = order.getOrderProducts();
        int size = orderProducts.size();

        this.orderedProductName = orderProducts.get(0).getProduct().getName() + (size > 1 ? " 외 " + (size - 1) + "개" : "");

        for (OrderProduct orderProduct : orderProducts) {
            long orderPrice = orderProduct.getOrderPrice();
            long orderQuantity = orderProduct.getOrderQuantity();

            this.orderedTotalAmount += orderPrice * orderQuantity;
        }
    }

}
