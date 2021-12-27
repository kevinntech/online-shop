package me.kevinntech.modules.products;

import lombok.*;
import me.kevinntech.modules.main.BaseEntity;
import me.kevinntech.modules.products.dto.ProductUpdateRequestDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_code", unique = true)
    private String code;

    @Column(name = "product_name")
    private String name;

    private String brand;

    @Column(name = "product_price")
    private long price;

    private String description;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String productImage;

    @Builder
    public Product(String code, String name, String brand, long price, String description, String productImage) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.productImage = productImage;
    }


    // 엔티티 수정
    public void update(ProductUpdateRequestDto requestDto) {
        this.code = requestDto.getCode();
        this.name = requestDto.getName();
        this.brand = requestDto.getBrand();
        this.price = requestDto.getPrice();
        this.description = requestDto.getDescription();
        this.productImage = requestDto.getProductImage();
    }

}
