package me.kevinntech.modules.users.controller;

import me.kevinntech.modules.users.dto.UserSaveRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute("userSaveRequestDto", new UserSaveRequestDto());
        return "users/sign-up";
    }

}