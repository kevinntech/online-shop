package me.kevinntech.modules.cart;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartRequestDto {

    private Long productId;

    private Long quantity;

}
