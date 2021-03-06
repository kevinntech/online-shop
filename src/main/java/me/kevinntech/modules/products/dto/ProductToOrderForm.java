package me.kevinntech.modules.products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kevinntech.modules.cart.CartProduct;
import me.kevinntech.modules.products.Product;

@Data
@NoArgsConstructor
public class ProductToOrderForm {

    private Long id;

    private String code;

    private String name;

    private String brand;

    private long price;

    private long totalQuantity; // 총 수량

    private long totalAmount;   // 총 금액

    private String description;

    private String productImage;

    public ProductToOrderForm(Product product, long quantity) {
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.productImage = product.getProductImage();
        this.totalQuantity = quantity;
        this.totalAmount = this.price * quantity;
    }

    public ProductToOrderForm(CartProduct cartProduct) {
        this.id = cartProduct.getProductId();
        this.code = cartProduct.getProductCode();
        this.name = cartProduct.getProductName();
        this.brand = cartProduct.getProductBrand();
        this.price = cartProduct.getProductPrice();
        this.description = cartProduct.getDescription();
        this.productImage = cartProduct.getProductImage();
        this.totalQuantity = cartProduct.getTotalQuantity();
        this.totalAmount = cartProduct.getTotalAmount();
    }

}
