package me.kevinntech.modules.cart;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.dto.ProductToOrderForm;
import me.kevinntech.modules.stock.Stock;
import me.kevinntech.modules.stock.StockRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final StockRepository stockRepository;

    @GetMapping("/carts")
    public String cart(@SessionAttribute(value = "cart", required = false) Cart cart, Model model){
        if (cart == null)
            cart = new Cart();

        model.addAttribute("cart", cart);

        // 장바구니 상품에 대한 재고 수량 데이터 표시
        List<Long> productIds = cart.getCartProducts().stream().map(cartProduct -> cartProduct.getProductId()).collect(Collectors.toList());
        List<Stock> stocks = stockRepository.findByProductIdIn(productIds);

        for (Stock stock : stocks) {
            for (CartProduct cartProduct : cart.getCartProducts()) {
                if (stock.getProduct().getId() == cartProduct.getProductId()) {
                    cartProduct.setStockQuantity(stock.getQuantity());
                }
            }
        }

        return "carts/view";
    }

    /*
     * 주문서에 보낼 데이터 설정
     * */
    @PostMapping("/carts")
    public String sendToOrderForm(@SessionAttribute(value = "cart", required = false) Cart cart, RedirectAttributes attributes, HttpSession httpSession) {
        if (cart == null)
            return "redirect:/carts";

        List<ProductToOrderForm> orderList = new ArrayList<>();
        Set<CartProduct> cartProducts = cart.getCartProducts();

        for (CartProduct cartProduct : cartProducts) {
            Stock findStock = stockRepository.findByProductId(cartProduct.getProductId());
            long totalQuantity = cartProduct.getTotalQuantity();

            // 현재 재고 수량이 주문 수량 보다 작다면
            if (findStock.getQuantity() < totalQuantity) {
                attributes.addFlashAttribute("error",cartProduct.getProductName() + "는 재고가 " + findStock.getQuantity() + "개 입니다.");
                return "redirect:/carts";
            }

            // 주문서에 보여줄 데이터 설정
            ProductToOrderForm responseDto = new ProductToOrderForm(cartProduct);
            orderList.add(responseDto);
        }

        cartProducts.clear(); // 장바구니에 저장했던 데이터 삭제
        attributes.addFlashAttribute("orderList", orderList);

        return "redirect:/orders/new";
    }
}
