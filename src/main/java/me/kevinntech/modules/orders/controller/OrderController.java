package me.kevinntech.modules.orders.controller;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.orders.OrderService;
import me.kevinntech.modules.orders.dto.OrderDetailsDto;
import me.kevinntech.modules.orders.dto.OrderSearchCond;
import me.kevinntech.modules.orders.dto.OrderViewResponseDto;
import me.kevinntech.modules.orders.enums.CodeType;
import me.kevinntech.modules.orders.enums.OrderStatus;
import me.kevinntech.modules.products.dto.ProductToOrderForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /*
    * 주문서 폼
    * */
    @GetMapping("/new")
    public String saveNewOrderForm(HttpServletRequest request, Model model){
        // "products/{productId}"에서 전달한 데이터
        List<ProductToOrderForm> orderList = (List<ProductToOrderForm>) model.asMap().get("orderList");

        String referer = request.getHeader("Referer");
        if(orderList == null){
            return "redirect:" + referer; // 이전 페이지로 돌아가기
        }

        long totalPaymentAmount = 0;

        for (ProductToOrderForm productToOrderForm : orderList) {
            totalPaymentAmount += productToOrderForm.getTotalAmount();
        }

        if(totalPaymentAmount <= 0){
            return "redirect:" + referer; // 이전 페이지로 돌아가기
        }

        model.addAttribute("totalPaymentAmount", totalPaymentAmount);

        return "orders/new";
    }

    @GetMapping("/list")
    public String ordersList(@ModelAttribute("orderSearchCond") OrderSearchCond orderSearchCond, Model model){
        List<OrderDetailsDto> orders = orderService.findOrderDetails(orderSearchCond);

        model.addAttribute("orders", orders);
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("codeTypes", CodeType.values());

        return "orders/list";
    }

}
