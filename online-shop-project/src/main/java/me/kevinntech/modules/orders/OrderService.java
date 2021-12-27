package me.kevinntech.modules.orders;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.orders.dto.OrderDetailsDto;
import me.kevinntech.modules.orders.dto.OrderSaveRequestDto;
import me.kevinntech.modules.orders.dto.OrderSearchCond;
import me.kevinntech.modules.orders.dto.OrderViewResponseDto;
import me.kevinntech.modules.orders.repository.OrderQueryRepository;
import me.kevinntech.modules.orders.repository.OrderRepository;
import me.kevinntech.modules.products.Product;
import me.kevinntech.modules.products.ProductRepository;
import me.kevinntech.modules.products.dto.ProductToOrderForm;
import me.kevinntech.modules.stock.Stock;
import me.kevinntech.modules.stock.StockRepository;
import me.kevinntech.modules.users.domain.User;
import me.kevinntech.modules.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final OrderQueryRepository orderQueryRepository;

    public Long saveNewOrder(User user, OrderSaveRequestDto requestDto) {
        User findUser = userRepository.findByEmail(user.getEmail());
        List<ProductToOrderForm> productToOrderForms = requestDto.getProductToOrderForms();
        List<OrderProduct> orderProducts = new ArrayList<>();

        // 주문 상품 생성
        for (ProductToOrderForm productToOrderForm : productToOrderForms) {
            Long productId = productToOrderForm.getId();
            Product product = productRepository.findOneById(productId);
            long totalQuantity = productToOrderForm.getTotalQuantity();
            long orderPrice = productToOrderForm.getPrice();

            OrderProduct orderProduct = OrderProduct.builder()
                    .product(product)
                    .orderPrice(orderPrice)
                    .orderQuantity(totalQuantity)
                    .build();

            Stock savedStock;

            if (stockRepository.existsStockByProductId(product.getId())) {
                savedStock = stockRepository.findByProductId(product.getId());

                if (savedStock == null)
                    return null;
            } else {
                Stock stock = new Stock(product, productToOrderForm.getPrice());
                savedStock = stockRepository.save(stock);
            }

            orderProduct.changeStock(savedStock);
            orderProducts.add(orderProduct);
        }

        // 주문 생성
        Order order = Order.builder()
                .user(findUser)
                .requestDto(requestDto)
                .orderProducts(orderProducts)
                .build();

        return orderRepository.save(order).getId();
    }

    public List<OrderViewResponseDto> findOrders() {
        return orderRepository.findAllOrderById().stream()
                .map(OrderViewResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDetailsDto> findOrderDetails(OrderSearchCond orderSearchCond) {
        List<OrderDetailsDto> orderDetails = orderQueryRepository.search(orderSearchCond);

        // 디스플레이용 순번 매기기
        processOrderDetailId(orderDetails);

        return orderDetails;
    }

    private void processOrderDetailId(List<OrderDetailsDto> orderDetails) {
        Map<Long, Long> orderDetailIds = new HashMap<>(); // <OrderId, LastOrderDetailId>

        for (OrderDetailsDto orderDetail : orderDetails) {
            long orderId = orderDetail.getId();
            long orderDetailId = orderDetailIds.getOrDefault(orderId, 0L) + 1;
            orderDetailIds.put(orderId, orderDetailId);

            orderDetail.setDisplayOrderDetailId(orderDetailId);
        }
    }

}
