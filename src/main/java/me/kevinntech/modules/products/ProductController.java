package me.kevinntech.modules.products;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.dto.ProductListResponseDto;
import me.kevinntech.modules.products.dto.ProductSaveRequestDto;
import me.kevinntech.modules.products.dto.ProductUpdateRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

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

}
