package me.kevinntech.modules.products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kevinntech.modules.products.Product;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class ProductUpdateRequestDto {

    private Long id;

    private String code;

    private String name;

    private String brand;

    @Min(0)
    private long price;

    private String description;

    private String productImage;

    public ProductUpdateRequestDto(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.productImage = product.getProductImage();
    }

    public Product toEntity() {
        return Product.builder()
                .code(code)
                .name(name)
                .brand(brand)
                .price(price)
                .description(description)
                .productImage(productImage)
                .build();
    }
}
