package me.kevinntech.modules.orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kevinntech.modules.orders.enums.CodeType;
import me.kevinntech.modules.orders.enums.OrderStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class OrderSearchCond {

    private CodeType codeType;

    private String searchCode;

    private OrderStatus orderStatus;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

}
