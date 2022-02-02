package me.kevinntech.modules.orders;

import lombok.*;
import me.kevinntech.modules.main.BaseEntity;
import me.kevinntech.modules.products.Product;
import me.kevinntech.modules.stock.Stock;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class OrderProduct extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private Long orderPrice;

    private Long orderQuantity;

    @Builder
    private OrderProduct(Product product, Long orderPrice, Long orderQuantity){
        this.product = product;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
    }

    /*
     * 연관관계 메소드
     * */
    public void changeOrder(Order order) {
        this.order = order;
    }

    public void changeStock(Stock stock) {
        this.stock = stock;
        stock.getOrderProductList().add(this); // OrderProduct와 Stock
        stock.adjustStock(); // 재고 조정
    }
}
