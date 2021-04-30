package me.kevinntech.modules.users.service;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.users.domain.User;
import me.kevinntech.modules.users.dto.UserCustom;
import me.kevinntech.modules.users.dto.UserSaveRequestDto;
import me.kevinntech.modules.users.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveNewUser(UserSaveRequestDto requestDto) {
        // 1) 회원 객체 생성
        User user = User.builder()
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword())) // 패스워드를 인코딩 한다.
                .build();

        user.completeSignUp();

        // 2) 회원 저장
        User newUser = userRepository.save(user); // userRepository로 user를 저장한다.

        return newUser;
    }

    public void login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserCustom(user),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
        // 처음에는 이메일을 입력할 것이라 가정한다.
        User user = userRepository.findByEmail(emailOrNickname);

        // 이메일로 유저를 찾았을 때, null이면
        if(user == null){
            // 닉네임으로 유저를 한번 더 찾아본다.
            user = userRepository.findByNickname(emailOrNickname);
        }

        // 그래도 account가 null이면 UsernameNotFoundException 예외를 발생 시킨다.
        if(user == null){
            throw new UsernameNotFoundException(emailOrNickname);
        }

        // 위의 과정을 통과 했다면 유저가 있다는 것이므로 Principal에 해당하는 객체(UserAccount)를 반환하면 된다.
        return new UserCustom(user);
    }
}