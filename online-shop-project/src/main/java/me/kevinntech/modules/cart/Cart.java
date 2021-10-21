package me.kevinntech.modules.cart;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Cart {

    private String customerName;

    private Set<CartProduct> cartProducts = new HashSet<>();

    public void addCartProduct(CartProduct cartProduct) {
        cartProducts.add(cartProduct);
    }

}
