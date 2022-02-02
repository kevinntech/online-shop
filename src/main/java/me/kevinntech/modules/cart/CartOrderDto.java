package me.kevinntech.modules.cart;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CartOrderDto {

    private List<CartProduct> cartProductList = new ArrayList<>();

}
