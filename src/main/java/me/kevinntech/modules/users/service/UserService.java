package me.kevinntech.modules.users.service;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.users.domain.User;
import me.kevinntech.modules.users.dto.UserSaveRequestDto;
import me.kevinntech.modules.users.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveNewUser(UserSaveRequestDto requestDto) {
        // 1) 회원 객체 생성
        User user = User.builder()
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword())) // 패스워드를 인코딩 한다.
                .build();

        // 2) 회원 저장
        User newUser = userRepository.save(user); // userRepository로 user를 저장한다.

        newUser.completeSignUp();
    }

}