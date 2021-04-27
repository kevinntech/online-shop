package me.kevinntech.modules.products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kevinntech.modules.products.Product;

@Data
@NoArgsConstructor
public class ProductListResponseDto {

    private Long id;

    private String code;

    private String name;

    private String brand;

    private long price;

    private String description;

    private String productImage;

    public ProductListResponseDto(Product product) {
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.productImage = product.getProductImage();
    }

}
