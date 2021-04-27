package me.kevinntech.modules.products;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.dto.ProductSaveRequestDto;
import me.kevinntech.modules.products.validator.ProductSaveValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    private final ProductSaveValidator productSaveValidator;

    /*
     * productSaveRequestDto에 대한 Validator 설정
     * */
    @InitBinder("productSaveRequestDto")
    public void initBinder(WebDataBinder webDataBinder){
        //Validator 설정은 WebDataBinder의 addValidators()로 한다.
        webDataBinder.addValidators(productSaveValidator);
    }

    /*
    * 상품 등록
    * */
    @PostMapping("/api/v1/products")
    public ResponseEntity save(@Valid @RequestBody ProductSaveRequestDto requestDto, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        // 상품 등록 처리
        productService.saveNewProduct(requestDto);

        return ResponseEntity.ok().build();
    }

    /*
    * 상품코드 중복 확인
    * */
    @PostMapping("/api/v1/products/validate")
    public ResponseEntity validate(@Valid @RequestBody ProductSaveRequestDto requestDto, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

}
