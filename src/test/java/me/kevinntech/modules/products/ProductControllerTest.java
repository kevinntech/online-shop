package me.kevinntech.modules.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kevinntech.modules.users.WithUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    @WithUser("kevin") // 해당 애노테이션이 없다면 인증을 요구하기 때문에 200 OK가 아닌 Redirection 됨
    @DisplayName("상품 등록 폼 조회")
    void saveNewProductForm() throws Exception {
        mockMvc.perform(get("/products/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/new"))
                .andExpect(model().attributeExists("productSaveRequestDto"));
    }

    @Test
    @WithUser("kevin")
    @DisplayName("상품 목록 조회")
    void productsList() throws Exception {
        mockMvc.perform(get("/products/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/list"))
                .andExpect(model().attributeExists("products"));
    }

}