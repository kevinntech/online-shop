package me.kevinntech.modules.main;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.ProductService;
import me.kevinntech.modules.products.dto.ProductListResponseDto;
import me.kevinntech.modules.users.CurrentUser;
import me.kevinntech.modules.users.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping("/")
    public String home(@CurrentUser User user, Model model){
        if(user != null){
            model.addAttribute(user);
        }

        List<ProductListResponseDto> products = productService.findProductsInStock();
        model.addAttribute("products", products);

        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
