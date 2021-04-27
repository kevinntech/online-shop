package me.kevinntech.modules.products;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.dto.ProductSaveRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @GetMapping("/new")
    public String saveNewProductForm(Model model){
        model.addAttribute("productSaveRequestDto", new ProductSaveRequestDto());
        return "products/new";
    }

}
