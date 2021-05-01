package me.kevinntech.modules.orders;

import lombok.*;
import me.kevinntech.modules.orders.dto.OrderSaveRequestDto;
import me.kevinntech.modules.orders.enums.DeliveryStatus;
import me.kevinntech.modules.orders.enums.OrderStatus;
import me.kevinntech.modules.users.domain.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)     //Order와 User는 다대일 관계이다.
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태 [ORDER, CANCEL]

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // 배송 상태

    private String orderedName; // 주문한 사람 이름

    private String deliveredName; // 받는 사람 이름

    private String address; // 주소

    private String phoneNumber; // 전화번호

    private String requestsForDelivery; // 배송 시 요청사항

}

