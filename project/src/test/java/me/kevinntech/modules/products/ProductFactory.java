package me.kevinntech.modules.products;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.dto.ProductSaveRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductFactory {

    @Autowired ProductRepository productRepository;

    public Product createProduct() {
        ProductSaveRequestDto requestDto = new ProductSaveRequestDto();
        requestDto.setCode("TEST-001");
        requestDto.setName("테스트 품목");
        requestDto.setBrand("테스트 브랜드");
        requestDto.setPrice(1000);
        requestDto.setDescription("설명");

        Product savedProduct;

        if(productRepository.existsByCode(requestDto.getCode()))
            savedProduct = productRepository.findOneByCode(requestDto.getCode());
        else
            savedProduct = productRepository.save(requestDto.toEntity());

        return savedProduct;
    }

}
