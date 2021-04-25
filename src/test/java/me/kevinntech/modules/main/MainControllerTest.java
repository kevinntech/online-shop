package me.kevinntech.modules.main;

import me.kevinntech.modules.users.dto.UserSaveRequestDto;
import me.kevinntech.modules.users.repository.UserRepository;
import me.kevinntech.modules.users.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    // 모든 테스트를 실행 할 때 마다 UserSaveRequestDto를 만든다.
    @BeforeEach
    void beforeEach(){
        UserSaveRequestDto requestDto = new UserSaveRequestDto();
        requestDto.setNickname("admin");
        requestDto.setEmail("admin@test.com");
        requestDto.setPassword("12345678");

        userService.saveNewUser(requestDto);
    }

    // 이메일이 중복되어 생성되지 않도록 삭제를 한다.
    @AfterEach
    void afterEach(){
        userRepository.deleteAll();
    }

    @DisplayName("이메일로 로그인 성공")
    @Test
    void login_with_email() throws Exception{
        mockMvc.perform(post("/login")
                .param("username", "admin@test.com")
                .param("password", "12345678")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("admin"));
        // kevin이라는 이름으로 인증 될 것이다.라고 예상한다.
        // 그 이유는 UserAccount의 생성자에서 username을 nickname으로 했기 때문이다.
    }

    @DisplayName("닉네임으로 로그인 성공")
    @Test
    void login_with_nickname() throws Exception{
        mockMvc.perform(post("/login")
                .param("username", "admin")
                .param("password", "12345678")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("admin"));
    }

    @DisplayName("로그인 실패")
    @Test
    void login_fail() throws Exception{
        // 로그인을 실패하는 경우에는 "/login?error"로 리다이렉트 하게되며 인증되지 않는다.
        mockMvc.perform(post("/login")
                .param("username", "1111111")
                .param("password", "000000000")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    @DisplayName("로그아웃")
    @Test
    void logout() throws Exception{
        mockMvc.perform(post("/logout")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated());
    }

}