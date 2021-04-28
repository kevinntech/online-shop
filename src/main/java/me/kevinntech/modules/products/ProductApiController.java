package me.kevinntech.modules.products;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.products.dto.ProductSaveRequestDto;
import me.kevinntech.modules.products.dto.ProductUpdateRequestDto;
import me.kevinntech.modules.products.validator.ProductSaveValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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
        Long savedId = productService.saveNewProduct(requestDto);

        System.out.println(savedId);

        return ResponseEntity.ok().build();
    }

    /*
    * 상품코드 중복 확인
    * */
    @PostMapping("/api/v1/products/validate")
    public ResponseEntity validate(@RequestBody Map<String, Object> param){
        String code = (String) param.get("code");
        boolean isDuplicate = productService.isDuplicate(code);

        if (isDuplicate) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    /*
    * 상품 수정
    * */
    @PutMapping("/api/v1/products/{productId}")
    public ResponseEntity update(@PathVariable Long productId, @Valid @RequestBody ProductUpdateRequestDto requestDto, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        // 상품 수정 처리
        productService.updateProduct(productId, requestDto);

        return ResponseEntity.ok().build();
    }

}
