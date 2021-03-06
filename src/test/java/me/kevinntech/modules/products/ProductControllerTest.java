package me.kevinntech.modules.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kevinntech.infra.MockMvcTest;
import me.kevinntech.modules.users.WithUser;
import me.kevinntech.modules.warehousing.Warehousing;
import me.kevinntech.modules.warehousing.WarehousingFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
public class ProductControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired ProductFactory productFactory;
    @Autowired WarehousingFactory warehousingFactory;

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

    @Test
    @WithUser("kevin")
    @DisplayName("상품 수정 폼 조회")
    void updateProductForm() throws Exception {
        Product product = productFactory.createProduct();

        mockMvc.perform(get("/products/" + product.getId() + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("products/edit"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    @WithUser("kevin")
    @DisplayName("상품 조회 폼 조회")
    void viewProductForm() throws Exception {
        Product product = productFactory.createProduct();

        mockMvc.perform(get("/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("products/view"))
                .andExpect(model().attributeExists("product", "stockQuantity"));
    }

    @Test
    @WithUser("kevin")
    @DisplayName("상품 컨트롤러에서 주문서로 데이터 전달")
    void sendToOrderForm() throws Exception {
        Product product = productFactory.createProduct();
        Warehousing warehousing = warehousingFactory.createWarehousing(product);

        mockMvc.perform(post("/products/" + product.getId())
                    .param("quantity", "1")
                    .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orders/new"))
                .andExpect(flash().attributeExists("orderList"));
    }

}
