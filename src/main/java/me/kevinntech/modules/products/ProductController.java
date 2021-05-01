package me.kevinntech.modules.products;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.dto.*;
import me.kevinntech.modules.stock.Stock;
import me.kevinntech.modules.stock.StockRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final StockRepository stockRepository;

    /*
    * 상품 등록 폼
    * */
    @GetMapping("/new")
    public String saveNewProductForm(Model model){
        model.addAttribute("productSaveRequestDto", new ProductSaveRequestDto());
        return "products/new";
    }

    /*
    * 상품 목록
    * */
    @GetMapping("/list")
    public String productsList(Model model){
        List<ProductListResponseDto> products = productService.findProducts();
        model.addAttribute("products", products);

        return "products/list";
    }

    /*
    * 상품 수정
    * */
    @GetMapping("/{productId}/edit")
    public String updateProductForm(@PathVariable Long productId, Model model){
        ProductUpdateRequestDto requestDto = productService.findOneById(productId);

        if(requestDto == null){
            model.addAttribute("error", "wrong.productId");
            return "products/list";
        }

        model.addAttribute("product", requestDto);

        return "products/edit";
    }

    /*
    * 상품 조회
    * */
    @GetMapping("/{productId}")
    public String viewProductForm(@PathVariable Long productId, Model model){
        Product product = productService.findById(productId);

        if(product == null){
            model.addAttribute("error", "wrong.productId");
            return "/";
        }

        ProductViewResponseDto responseDto = new ProductViewResponseDto(product);
        model.addAttribute("product", responseDto);

        return "products/view";
    }

    /*
    * 주문서에 보낼 데이터 설정
    * */
    @PostMapping("/{productId}")
    public String sendToOrderForm(@PathVariable Long productId, @RequestParam("quantity") Long quantity, Model model, RedirectAttributes attributes){
        Product product = productService.findById(productId);

        if(product == null){
            attributes.addFlashAttribute("error", "존재하지 않는 상품입니다.");
            return "redirect:/products/" + product.getId();
        }

        Stock findStock = stockRepository.findByProductId(product.getId());

        // 현재 재고 수량이 주문 수량 보다 작다면
        if(findStock.getQuantity() < quantity){
            attributes.addFlashAttribute("error", "현재 재고 수량은 " + findStock.getQuantity() + "개입니다.");
            return "redirect:/products/" + product.getId();
        }

        // 주문서에 보여줄 데이터 설정
        ProductToOrderForm responseDto = new ProductToOrderForm(product, quantity);
        List<ProductToOrderForm> orderList = new ArrayList<>();
        orderList.add(responseDto);

        attributes.addFlashAttribute("orderList", orderList);

        return "redirect:/orders/new";
    }

}
