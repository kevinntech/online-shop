package me.kevinntech.modules.warehousing;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.ProductService;
import me.kevinntech.modules.products.dto.ProductListResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehousingService {

    private final ProductService productService;

    public List<ProductListResponseDto> findProductsForWarehousing() {
        return productService.findProducts();
    }

}

