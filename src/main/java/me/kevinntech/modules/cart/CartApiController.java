package me.kevinntech.modules.cart;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.main.ErrorCode;
import me.kevinntech.modules.main.exception.DataNotFoundException;
import me.kevinntech.modules.main.exception.NotValidArgumentException;
import me.kevinntech.modules.products.Product;
import me.kevinntech.modules.products.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@SessionAttributes("cart")
@RequiredArgsConstructor
public class CartApiController {

    private final ProductService productService;

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

    @PostMapping("/api/v1/carts")
    public ResponseEntity addToCart(@RequestBody CartRequestDto requestDto, @ModelAttribute Cart cart) {
        Long productId = requestDto.getProductId();
        Long quantity = requestDto.getQuantity();

        Product product = productService.findById(productId);

        if (quantity == 0)
            throw new NotValidArgumentException(ErrorCode.INVALID_INPUT_VALUE.getMessage());

        if (product == null)
            throw new DataNotFoundException(ErrorCode.NOT_FOUND_VALUE.getMessage());

        CartProduct cartProduct = new CartProduct(product, quantity);
        cart.addCartProduct(cartProduct);

        return ResponseEntity.ok().build();
    }

    /*
    * 장바구니(세션)에서 상품 제거
    * */
    @DeleteMapping("/api/v1/carts/{productId}")
    public ResponseEntity deleteFromCart(@PathVariable Long productId, @ModelAttribute Cart cart) {
        Set<CartProduct> cartProducts = cart.getCartProducts();
        boolean isRemoved = false;

        for (CartProduct cartProduct : cartProducts) {
            if (productId == cartProduct.getProductId()) {
                isRemoved = true;
                cartProducts.remove(cartProduct);
                break;
            }
        }

        if (isRemoved == false)
            throw new DataNotFoundException(ErrorCode.NOT_FOUND_VALUE.getMessage());

        return ResponseEntity.ok().build();
    }

}
