package me.kevinntech.modules.orders;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.main.exception.NotValidArgumentException;
import me.kevinntech.modules.orders.dto.OrderSaveRequestDto;
import me.kevinntech.modules.users.CurrentUser;
import me.kevinntech.modules.users.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders")
    public ResponseEntity save(@CurrentUser User user, @Valid @RequestBody OrderSaveRequestDto requestDto, Errors errors){
        if (errors.hasErrors())
            throw new NotValidArgumentException();

        // 주문 등록 처리
        orderService.saveNewOrder(user, requestDto);

        return ResponseEntity.ok().build();
    }

}
