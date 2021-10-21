package me.kevinntech.modules.cart;

import lombok.RequiredArgsConstructor;
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

        if (product == null || quantity == 0) {
            return ResponseEntity.badRequest().build();
        }

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

        if (isRemoved == false) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

}
