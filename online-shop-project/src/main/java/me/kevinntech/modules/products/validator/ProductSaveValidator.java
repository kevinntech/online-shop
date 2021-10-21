package me.kevinntech.modules.products.validator;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.ProductRepository;
import me.kevinntech.modules.products.dto.ProductSaveRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ProductSaveValidator implements Validator {

    private final ProductRepository productRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ProductSaveRequestDto.class);  // ProductSaveRequestDto 타입에 대해 검증을 한다.
    }

    @Override
    public void validate(Object target, Errors errors) {
        // 상품 코드 중복 여부 검증
        ProductSaveRequestDto requestDto = (ProductSaveRequestDto) target;

        // 리포지토리(DB)에 해당 Code가 있는지 검사하여 있다면 에러 코드를 추가한다.
        if(productRepository.existsByCode(requestDto.getCode())){
            errors.rejectValue("code", "invalid.code", new Object[]{requestDto.getCode()}, "이미 사용중인 상품코드 입니다.");
        }
    }

}
