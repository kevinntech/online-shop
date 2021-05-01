package me.kevinntech.modules.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kevinntech.infra.MockMvcTest;
import me.kevinntech.modules.orders.dto.OrderSaveRequestDto;
import me.kevinntech.modules.products.Product;
import me.kevinntech.modules.products.ProductFactory;
import me.kevinntech.modules.products.dto.ProductToOrderForm;
import me.kevinntech.modules.stock.Stock;
import me.kevinntech.modules.stock.StockRepository;
import me.kevinntech.modules.users.WithUser;
import me.kevinntech.modules.warehousing.Warehousing;
import me.kevinntech.modules.warehousing.WarehousingFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class OrderApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired ProductFactory productFactory;
    @Autowired WarehousingFactory warehousingFactory;
    @Autowired StockRepository stockRepository;

    @Test
    @WithUser("kevin")
    @DisplayName("주문 등록 - 완료")
    public void saveOrder_success() throws Exception {
        // 상품 등록
        Product product = productFactory.createProduct();
        // 상품 입고
        Warehousing warehousing = warehousingFactory.createWarehousing(product);

        // 상품 주문
        int orderQuantity = 10;
        ProductToOrderForm responseDto = new ProductToOrderForm(product, orderQuantity);
        List<ProductToOrderForm> orderList = new ArrayList<>();
        orderList.add(responseDto);

        OrderSaveRequestDto requestDto = new OrderSaveRequestDto();
        requestDto.setProductToOrderForms(orderList);
        requestDto.setDeliveredName("홍길동");
        requestDto.setAddress("주소");
        requestDto.setPhoneNumber("010-1111-1111");
        requestDto.setRequestsForDelivery("배송 시 요청사항");

        String jsonString = objectMapper.writeValueAsString(requestDto);

        this.mockMvc.perform(post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonString)
                    .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

        /*
        * [재고 수량 확인]
        * 재고 수량 = 입고 수량 - 주문 수량
        * */
        Stock stock = stockRepository.findByProductId(product.getId());
        assertThat(stock.getQuantity()).isEqualTo(warehousing.getQuantity() - orderQuantity);
    }

    @Test
    @WithUser("kevin")
    @DisplayName("주문 등록 - 실패")
    public void saveOrder_fail() throws Exception {
        Product product = productFactory.createProduct();

        int orderQuantity = 10;
        ProductToOrderForm responseDto = new ProductToOrderForm(product, orderQuantity);
        List<ProductToOrderForm> orderList = new ArrayList<>();
        orderList.add(responseDto);

        OrderSaveRequestDto requestDto = new OrderSaveRequestDto();
        requestDto.setProductToOrderForms(orderList);
        requestDto.setRequestsForDelivery("배송 시 요청사항");

        String jsonString = objectMapper.writeValueAsString(requestDto);

        this.mockMvc.perform(post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonString)
                    .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}