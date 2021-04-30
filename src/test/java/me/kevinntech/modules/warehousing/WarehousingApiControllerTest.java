package me.kevinntech.modules.warehousing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kevinntech.modules.products.Product;
import me.kevinntech.modules.products.ProductFactory;
import me.kevinntech.modules.products.ProductRepository;
import me.kevinntech.modules.products.dto.ProductSaveRequestDto;
import me.kevinntech.modules.stock.StockRepository;
import me.kevinntech.modules.warehousing.dto.WarehousingSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WarehousingApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired ProductFactory productFactory;
    @Autowired WarehousingRepository warehousingRepository;
    @Autowired StockRepository stockRepository;

    @Test
    @DisplayName("상품 입고 - 완료")
    void saveNewWarehousing_success() throws Exception {
        Product product = productFactory.createProduct();

        WarehousingSaveRequestDto requestDto = new WarehousingSaveRequestDto();
        requestDto.setProductId(product.getId());
        requestDto.setWarehousingPrice(1000);
        requestDto.setWarehousingQuantity(10);

        String jsonString = objectMapper.writeValueAsString(requestDto);

        this.mockMvc.perform(post("/api/v1/warehousing")
                    .contentType(MediaType.APPLICATION_JSON) // HTTP 요청 본문으로 JSON을 보내며
                    .accept(MediaType.APPLICATION_JSON) // JSON으로 응답이 오길 바란다.
                    .content(jsonString)
                    .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        boolean existsStock = stockRepository.existsStockByProductId(product.getId());

        assertThat(existsStock).isTrue();
    }

    @Test
    @DisplayName("상품 입고 - 실패")
    void saveNewWarehousing_fail() throws Exception {
        Product product = productFactory.createProduct();

        WarehousingSaveRequestDto requestDto = new WarehousingSaveRequestDto();
        requestDto.setProductId(product.getId());
        requestDto.setWarehousingPrice(-1);
        requestDto.setWarehousingQuantity(-1);

        String jsonString = objectMapper.writeValueAsString(requestDto);

        this.mockMvc.perform(post("/api/v1/warehousing")
                    .contentType(MediaType.APPLICATION_JSON) // HTTP 요청 본문으로 JSON을 보내며
                    .accept(MediaType.APPLICATION_JSON) // JSON으로 응답이 오길 바란다.
                    .content(jsonString)
                    .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
