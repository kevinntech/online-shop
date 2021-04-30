package me.kevinntech.modules.users.controller;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.users.domain.User;
import me.kevinntech.modules.users.dto.UserSaveRequestDto;
import me.kevinntech.modules.users.service.UserService;
import me.kevinntech.modules.users.validator.UserSaveValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserSaveValidator userSaveValidator;
    private final UserService userService;

    /*
     * UserSaveRequestDto이라는 데이터를 받을 때, 사용 할 Validator를 설정한다.
     * userSaveRequestDto는 타입(UserSaveRequestDto)의 캐멀 케이스와 같다.
     * */
    @InitBinder("userSaveRequestDto")
    public void initBinder(WebDataBinder webDataBinder){
        //Validator 설정은 WebDataBinder의 addValidators()로 한다.
        webDataBinder.addValidators(userSaveValidator);
    }

    @PostMapping("/api/v1/users")
    public ResponseEntity saveNewUser(@Valid @RequestBody UserSaveRequestDto requestDto, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        // 회원 가입 처리
        User user = userService.saveNewUser(requestDto);
        userService.login(user);

        return ResponseEntity.ok().build();
    }

}
