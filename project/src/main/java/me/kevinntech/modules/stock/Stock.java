package me.kevinntech.modules.stock;

import lombok.*;
import me.kevinntech.modules.orders.OrderProduct;
import me.kevinntech.modules.products.Product;
import me.kevinntech.modules.warehousing.Warehousing;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Stock {

    @Id @GeneratedValue
    @Column(name = "stock_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "stock")
    @Column(name = "orderProduct_id")
    private List<OrderProduct> orderProductList = new ArrayList<>();

    @OneToMany(mappedBy = "stock")
    @Column(name = "warehousing_id")
    private List<Warehousing> warehousingList = new ArrayList<>();

    @Column(name = "stock_price")
    private long price;

    @Column(name = "stock_quantity")
    private long quantity;

    public Stock(Product product, long price){
        this.product = product;
        this.price = price;
    }

    public void adjustStock(){
        // 재고 조정
        this.quantity = 0;

        // 입고(+)
        for (Warehousing warehousing : warehousingList) {
            this.quantity += warehousing.getQuantity();
        }

        // 주문(-)
        for(OrderProduct orderProduct : orderProductList){
            this.quantity -= orderProduct.getOrderQuantity();
        }
    }

}


