package me.kevinntech.modules.warehousing;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.Product;
import me.kevinntech.modules.warehousing.dto.WarehousingSaveRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehousingFactory {

    @Autowired WarehousingService warehousingService;

    public Warehousing createWarehousing(Product product) {
        WarehousingSaveRequestDto requestDto = new WarehousingSaveRequestDto();
        requestDto.setProductId(product.getId());
        requestDto.setWarehousingQuantity(100);
        requestDto.setWarehousingPrice(1000);

        Warehousing savedWarehousing = warehousingService.saveNewWarehousing(requestDto);

        return savedWarehousing;
    }

}
