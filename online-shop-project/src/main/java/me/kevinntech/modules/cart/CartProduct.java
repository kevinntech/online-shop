package me.kevinntech.modules.cart;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.kevinntech.modules.products.Product;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "productId")
public class CartProduct {

    private Long productId;

    private String productCode;

    private String productName;

    private String productBrand;

    private long productPrice;

    private long totalQuantity;

    private long totalAmount;

    private String description;

    private String productImage;

    private long stockQuantity;

    public CartProduct(Product product, Long quantity) {
        productId = product.getId();
        productCode = product.getCode();
        productName = product.getName();
        productBrand = product.getBrand();
        productPrice = product.getPrice();
        totalQuantity = quantity;
        totalAmount = productPrice * totalQuantity;
        description = product.getDescription();
        productImage = product.getProductImage();
    }
}
