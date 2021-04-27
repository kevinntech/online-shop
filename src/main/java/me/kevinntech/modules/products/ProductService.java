package me.kevinntech.modules.products;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.dto.ProductListResponseDto;
import me.kevinntech.modules.products.dto.ProductSaveRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    /*
    * 상품 목록 조회
    * */
    public List<ProductListResponseDto> findProducts() {
        return productRepository.findAllOrderById().stream()
                .map(ProductListResponseDto::new)
                .collect(Collectors.toList());
    }

    public boolean isDuplicate(String code) {
        if(productRepository.existsByCode(code))
            return true;

        return false;
    }

}
