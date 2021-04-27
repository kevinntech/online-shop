package me.kevinntech.modules.products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kevinntech.modules.products.Product;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ProductSaveRequestDto {

    @NotBlank
    @Length(min = 1, max = 50)
    private String code;

    @NotBlank
    @Length(min = 1, max = 50)
    private String name;

    @NotBlank
    @Length(min = 1, max = 50)
    private String brand;

    @Min(0)
    private long price;

    private String description;

    private String productImage;

    public ProductSaveRequestDto(Product product) {
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
