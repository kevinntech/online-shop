package me.kevinntech.modules.warehousing.validator;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.ProductRepository;
import me.kevinntech.modules.warehousing.dto.WarehousingSaveRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class WarehousingSaveValidator implements Validator {

    private final ProductRepository productRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(WarehousingSaveRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WarehousingSaveRequestDto requestDto = (WarehousingSaveRequestDto) target;

        if(!productRepository.existsById(requestDto.getProductId())){ // 존재하지 않는다면
            errors.rejectValue("id", "invalid.id", new Object[]{requestDto.getProductId()}, "잘못된 상품번호 입니다.");
        }
    }
}