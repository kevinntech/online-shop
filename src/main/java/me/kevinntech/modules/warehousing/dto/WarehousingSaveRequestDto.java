package me.kevinntech.modules.warehousing.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.kevinntech.modules.warehousing.Warehousing;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class WarehousingSaveRequestDto {

    private Long productId;

    @Min(0)
    private long warehousingPrice;

    @Min(0)
    private long warehousingQuantity;

    public WarehousingSaveRequestDto(Warehousing warehousing) {
        this.productId = warehousing.getProduct().getId();
        this.warehousingPrice = warehousing.getPrice();
        this.warehousingQuantity = warehousing.getQuantity();
    }

}