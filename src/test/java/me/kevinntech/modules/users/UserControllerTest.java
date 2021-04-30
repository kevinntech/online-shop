package me.kevinntech.modules.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kevinntech.infra.MockMvcTest;
import me.kevinntech.modules.users.domain.User;
import me.kevinntech.modules.users.dto.UserSaveRequestDto;
import me.kevinntech.modules.users.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class UserControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;
    @Autowired ObjectMapper objectMapper;

    @DisplayName("회원 가입 화면이 보이는지 테스트")
    @Test
    void signUpForm() throws Exception {
        mockMvc.perform(get("/users/sign-up"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users/sign-up"))
                .andExpect(model().attributeExists("userSaveRequestDto"));
    }

    @DisplayName("회원 가입 처리 - 입력 값 오류")
    @Test
    void saveNewUser_with_wrong_input() throws Exception {
        UserSaveRequestDto requestDto = new UserSaveRequestDto();
        requestDto.setNickname("test");
        requestDto.setEmail("email..");
        requestDto.setPassword("12345");

        String jsonString = objectMapper.writeValueAsString(requestDto);

        // 폼을 제출(submit)하는 테스트 코드를 작성할 때는 csrf 토큰을 추가해야 한다.
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON) // HTTP 요청 본문으로 JSON을 보낸다.
                .content(jsonString)
                .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(unauthenticated()); // 로그인 테스트 코드 (로그인 X)
    }

    @DisplayName("회원 가입 처리 - 입력 값 정상")
    @Test
    void saveNewUser_with_correct_input() throws Exception {
        UserSaveRequestDto requestDto = new UserSaveRequestDto();
        requestDto.setNickname("test");
        requestDto.setEmail("test@test.com");
        requestDto.setPassword("12345678");

        String jsonString = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/api/v1/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonString)
                    .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(authenticated());  // 로그인 테스트 코드 (로그인 O)

        User user = userRepository.findByEmail("test@test.com");
        assertThat(user).isNotNull();
        assertThat(user.getPassword()).isNotEqualTo("12345678"); // 패스워드가 인코딩 되었는지 확인
    }

}