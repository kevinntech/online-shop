package me.kevinntech.modules.products;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.dto.ProductListResponseDto;
import me.kevinntech.modules.products.dto.ProductSaveRequestDto;
import me.kevinntech.modules.products.dto.ProductUpdateRequestDto;
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

    /*
    * 상품 단 건 조회 (For Update)
    * */
    public ProductUpdateRequestDto findOneById(Long productId) {
        Product product = productRepository.findOneById(productId);

        if(product == null)
            return null;

        ProductUpdateRequestDto requestDto = new ProductUpdateRequestDto();
        requestDto.setId(product.getId());
        requestDto.setCode(product.getCode());
        requestDto.setName(product.getName());
        requestDto.setBrand(product.getBrand());
        requestDto.setPrice(product.getPrice());
        requestDto.setDescription(product.getDescription());
        requestDto.setProductImage(product.getProductImage());

        return requestDto;
    }

    // 변경 감지 기능을 사용해서 수정하기
    @Transactional
    public void updateProduct(Long productId, ProductUpdateRequestDto requestDto) {
        Product findProduct = productRepository.findOneById(productId);

        findProduct.update(requestDto);
    }
}
