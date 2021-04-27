package me.kevinntech.modules.products;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.dto.ProductSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /*
     * 등록
     * */
    @Transactional
    public Long saveNewProduct(ProductSaveRequestDto requestDto) {
        return productRepository.save(requestDto.toEntity()).getId();
    }

}
