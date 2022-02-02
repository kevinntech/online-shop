package me.kevinntech.modules.users.validator;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.users.dto.UserSaveRequestDto;
import me.kevinntech.modules.users.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserSaveValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserSaveRequestDto.class); // UserSaveRequestDto 타입에 대해 검증을 한다.
    }

    @Override
    public void validate(Object target, Errors errors) {
        // email, nickname 중복 여부 검증
        UserSaveRequestDto requestDto = (UserSaveRequestDto) target;

        // 리포지토리(DB)에 해당 Email이 있는지 검사하여 있다면 에러 코드를 추가한다.
        if(userRepository.existsByEmail(requestDto.getEmail())){
            errors.rejectValue("email", "invalid.email", new Object[]{requestDto.getEmail()}, "이미 사용중인 이메일 입니다.");
        }

        // 리포지토리(DB)에 해당 Nickname이 있는지 검사하여 있다면 에러 코드를 추가한다.
        if(userRepository.existsByNickname(requestDto.getNickname())){
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{requestDto.getEmail()}, "이미 사용중인 닉네임입니다.");
        }
    }
}
