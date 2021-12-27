package me.kevinntech.modules.orders.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.orders.Order;
import me.kevinntech.modules.orders.QOrderProduct;
import me.kevinntech.modules.orders.dto.OrderDetailsDto;
import me.kevinntech.modules.orders.dto.OrderSearchCond;
import me.kevinntech.modules.orders.dto.QOrderDetailsDto;
import me.kevinntech.modules.orders.enums.CodeType;
import me.kevinntech.modules.orders.enums.OrderStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static me.kevinntech.modules.orders.QOrder.order;
import static me.kevinntech.modules.orders.QOrderProduct.orderProduct;
import static me.kevinntech.modules.products.QProduct.product;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Order> getOrders() {
        return queryFactory
                .selectFrom(order)
                .fetch();
    }

    public List<OrderDetailsDto> search(OrderSearchCond searchCond) {
        List<OrderDetailsDto> result = queryFactory
                .select(new QOrderDetailsDto(
                        order.id, product.code, product.name, orderProduct.orderPrice, orderProduct.orderQuantity,
                        orderProduct.orderPrice.multiply(orderProduct.orderQuantity), order.orderStatus, order.deliveryStatus,
                        order.orderedName, order.deliveredName, order.address, order.phoneNumber, order.createdAt))
                .from(orderProduct)
                .join(orderProduct.order, order)
                .join(orderProduct.product, product)
                .where(codeTypeAndSearchCodeEq(searchCond.getCodeType(), searchCond.getSearchCode()),
                        orderStatusEq(searchCond.getOrderStatus()),
                        orderDateTimeBetween(searchCond.getStartDate(), searchCond.getEndDate()))
                .orderBy(order.id.asc())
                .fetch();

        return result;
    }

    private BooleanExpression orderDateTimeBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null)
            return null;

        return order.createdAt.between(startDate.atStartOfDay(), endDate.atTime(23, 59));
    }

    private BooleanExpression orderStatusEq(OrderStatus orderStatus) {
        return orderStatus != null ? order.orderStatus.eq(orderStatus) : null;
    }

    private BooleanExpression codeTypeAndSearchCodeEq(CodeType codeType, String searchCode) {
        if (codeType == null || !StringUtils.hasLength(searchCode)) {
            return null;
        }

        if (codeType.getValue().equals(CodeType.ORDER_NUMBER.getValue())) {
            // 숫자 형식이 아니면 조건을 적용하지 않음
            if (!searchCode.matches("[0-9]+")) {
                return null;
            }

            return order.id.eq(Long.valueOf(searchCode));
        } else if (codeType.getValue().equals(CodeType.PRODUCT_CODE.getValue())) {
            return product.code.eq(searchCode);
        } else {
            return null;
        }
    }

}
