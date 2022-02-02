package me.kevinntech.modules.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kevinntech.infra.MockMvcTest;
import me.kevinntech.modules.products.dto.ProductSaveRequestDto;
import me.kevinntech.modules.users.WithUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class ProductApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired ProductFactory productFactory;
    @Autowired ProductRepository productRepository;

    @Test
    @WithUser("kevin")
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
    @WithUser("kevin")
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

    @Test
    @WithUser("kevin")
    @DisplayName("상품 수정 - 완료")
    public void updateProduct_success() throws Exception {
        Product product = productFactory.createProduct();

        ProductSaveRequestDto requestDto = new ProductSaveRequestDto();
        requestDto.setCode("NO-999");
        requestDto.setName("변경된 상품명");
        requestDto.setBrand("변경된 브랜드");

        String jsonString = objectMapper.writeValueAsString(requestDto);

        this.mockMvc.perform(put("/api/v1/products/" +  product.getId())
                    .contentType(MediaType.APPLICATION_JSON) // HTTP 요청 본문으로 JSON을 보내며
                    .accept(MediaType.APPLICATION_JSON) // JSON으로 응답이 오길 바란다.
                    .content(jsonString)
                    .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        Product findProduct = productRepository.findOneById(product.getId());

        assertThat(findProduct).isEqualTo(product);
        assertThat(findProduct.getCode()).isEqualTo("NO-999");
        assertThat(findProduct.getName()).isEqualTo("변경된 상품명");
        assertThat(findProduct.getBrand()).isEqualTo("변경된 브랜드");
    }

    @Test
    @WithUser("kevin")
    @DisplayName("상품 수정 - 실패")
    public void updateProduct_fail() throws Exception {
        Product product = productFactory.createProduct();

        ProductSaveRequestDto requestDto = new ProductSaveRequestDto();
        requestDto.setCode("NO-999");
        requestDto.setName("변경된 상품명");
        requestDto.setBrand("변경된 브랜드");
        requestDto.setPrice(-1);

        String jsonString = objectMapper.writeValueAsString(requestDto);

        this.mockMvc.perform(put("/api/v1/products/" +  product.getId())
                    .contentType(MediaType.APPLICATION_JSON) // HTTP 요청 본문으로 JSON을 보내며
                    .accept(MediaType.APPLICATION_JSON) // JSON으로 응답이 오길 바란다.
                    .content(jsonString)
                    .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUser("kevin")
    @DisplayName("상품 삭제 - 완료")
    public void deleteProduct_success() throws Exception {
        Product product = productFactory.createProduct();

        this.mockMvc.perform(delete("/api/v1/products/" +  product.getId())
                    .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        Product findProduct = productRepository.findOneById(product.getId());

        assertThat(findProduct).isNull();
    }

    @Test
    @WithUser("kevin")
    @DisplayName("상품 삭제 - 실패")
    public void deleteProduct_fail() throws Exception {
        this.mockMvc.perform(delete("/api/v1/products/" + -1)
                    .with(csrf()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}