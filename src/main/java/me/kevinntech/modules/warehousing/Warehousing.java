package me.kevinntech.modules.warehousing;

import lombok.*;
import me.kevinntech.modules.main.BaseEntity;
import me.kevinntech.modules.products.Product;
import me.kevinntech.modules.stock.Stock;
import me.kevinntech.modules.warehousing.dto.WarehousingSaveRequestDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Warehousing extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "warehousing_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "warehousing_price")
    private long price;

    @Column(name = "warehousing_quantity")
    private long quantity;

    @Builder
    private Warehousing(Product product, Stock stock, WarehousingSaveRequestDto requestDto){
        this.product = product;
        this.price = requestDto.getWarehousingPrice();
        this.quantity = requestDto.getWarehousingQuantity();

        /*
         * 연관관계 설정
         * */
        this.stock = stock;
        stock.getWarehousingList().add(this); // Warehousing과 Stock
        stock.adjustStock(); // 재고 조정
    }

}