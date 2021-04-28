package me.kevinntech.modules.warehousing;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.dto.ProductListResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/warehousing")
@RequiredArgsConstructor
public class WarehousingController {

    private final WarehousingService warehousingService;

    @GetMapping("/new")
    public String saveNewWarehousingForm(Model model){
        List<ProductListResponseDto> products = warehousingService.findProductsForWarehousing();
        model.addAttribute("products", products);

        return "warehousing/new";
    }

}
