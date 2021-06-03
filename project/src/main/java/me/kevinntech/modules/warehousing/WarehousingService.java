package me.kevinntech.modules.warehousing;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.Product;
import me.kevinntech.modules.products.ProductService;
import me.kevinntech.modules.products.dto.ProductListResponseDto;
import me.kevinntech.modules.stock.Stock;
import me.kevinntech.modules.stock.StockRepository;
import me.kevinntech.modules.warehousing.dto.WarehousingSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehousingService {

    private final WarehousingRepository warehousingRepository;
    private final StockRepository stockRepository;
    private final ProductService productService;

    public List<ProductListResponseDto> findProductsForWarehousing() {
        return productService.findProducts();
    }

    /*
    * 상품 입고 처리
    * */
    @Transactional
    public Warehousing saveNewWarehousing(WarehousingSaveRequestDto requestDto) {
        Product product = productService.findById(requestDto.getProductId());

        if(product == null){
            return null;
        }

        Stock savedStock;

        if(stockRepository.existsStockByProductId(product.getId())){
            savedStock = stockRepository.findByProductId(product.getId());

            if (savedStock == null)
                return null;
        }else{
            Stock stock = new Stock(product, requestDto.getWarehousingPrice());
            savedStock = stockRepository.save(stock);
        }

        Warehousing warehousing = Warehousing.builder()
                .product(product)
                .stock(savedStock)
                .requestDto(requestDto)
                .build();

        Warehousing savedWarehousing = warehousingRepository.save(warehousing);

        return savedWarehousing;
    }
}

