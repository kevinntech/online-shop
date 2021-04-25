package me.kevinntech.modules.main;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.users.CurrentUser;
import me.kevinntech.modules.users.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String home(@CurrentUser User user, Model model){
        if(user != null){
            model.addAttribute(user);
        }

        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
