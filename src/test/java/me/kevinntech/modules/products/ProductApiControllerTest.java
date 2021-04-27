package me.kevinntech.modules.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kevinntech.modules.products.dto.ProductSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("상품 등록 - 완료")
    public void createProduct_success() throws Exception {
        ProductSaveRequestDto requestDto = new ProductSaveRequestDto();
        requestDto.setCode("NO-1");
        requestDto.setName("운동화");
        requestDto.setBrand("브랜드");
        requestDto.setPrice(0);
        requestDto.setDescription("운동화 입니다.");

        String jsonString = objectMapper.writeValueAsString(requestDto);

        this.mockMvc.perform(post("/api/v1/products")
                    .contentType(MediaType.APPLICATION_JSON) // HTTP 요청 본문으로 JSON을 보내며
                    .accept(MediaType.APPLICATION_JSON) // JSON으로 응답이 오길 바란다.
                    .content(jsonString)
                    .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 등록 - 실패")
    public void createProduct_fail() throws Exception {
        ProductSaveRequestDto requestDto = new ProductSaveRequestDto();
        requestDto.setCode("NO111NO111NO111NO111NO111NO111NO111NO111NO111NO111NO111NO111");
        requestDto.setName("운동화운동화운동화운동화운동화운동화운동화운동화운동화운동화운동화운동화운동화운동화");
        requestDto.setBrand("브랜드");
        requestDto.setPrice(-1); // 마이너스 값은 들어 올 수 없음
        requestDto.setDescription("운동화 입니다.");

        String jsonString = objectMapper.writeValueAsString(requestDto);

        this.mockMvc.perform(post("/api/v1/products")
                    .contentType(MediaType.APPLICATION_JSON) // HTTP 요청 본문으로 JSON을 보내며
                    .accept(MediaType.APPLICATION_JSON) // JSON으로 응답이 오길 바란다.
                    .content(jsonString)
                    .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}