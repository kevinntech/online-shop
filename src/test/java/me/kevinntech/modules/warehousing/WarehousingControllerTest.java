package me.kevinntech.modules.warehousing;

import me.kevinntech.modules.users.WithUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WarehousingControllerTest {

    @Autowired MockMvc mockMvc;

    @Test
    @WithUser("kevin")
    @DisplayName("상품 입고 폼 조회")
    void saveNewWarehousingForm() throws Exception {
        mockMvc.perform(get("/warehousing/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("warehousing/new"))
                .andExpect(model().attributeExists("products"));
    }

}