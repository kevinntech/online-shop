package me.kevinntech.modules.orders;

import me.kevinntech.infra.MockMvcTest;
import me.kevinntech.modules.products.Product;
import me.kevinntech.modules.products.ProductFactory;
import me.kevinntech.modules.products.dto.ProductToOrderForm;
import me.kevinntech.modules.users.WithUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class OrderControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ProductFactory productFactory;

    @Test
    @WithUser("kevin")
    @DisplayName("주문서 폼 조회 - 완료")
    void saveNewOrderForm_success() throws Exception {
        Product product = productFactory.createProduct();

        ProductToOrderForm responseDto = new ProductToOrderForm(product, 10);
        List<ProductToOrderForm> orderList = new ArrayList<>();
        orderList.add(responseDto);

        mockMvc.perform(get("/orders/new")
                    .flashAttr("orderList", orderList))
                .andExpect(status().isOk())
                .andExpect(view().name("orders/new"))
                .andExpect(model().attributeExists("orderList"))
                .andExpect(model().attributeExists("totalPaymentAmount"));
    }

    @Test
    @WithUser("kevin")
    @DisplayName("주문서 폼 조회 - 실패")
    void saveNewOrderForm_fail() throws Exception {
        Product product = productFactory.createProduct();

        ProductToOrderForm responseDto = new ProductToOrderForm(product, 0);
        List<ProductToOrderForm> orderList = new ArrayList<>();
        orderList.add(responseDto);

        mockMvc.perform(get("/orders/new")
                    .flashAttr("orderList", orderList))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUser("kevin")
    @DisplayName("주문 내역 조회")
    void ordersList() throws Exception {
        mockMvc.perform(get("/orders/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders/list"))
                .andExpect(model().attributeExists("orders"));
    }

}