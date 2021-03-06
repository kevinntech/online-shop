package me.kevinntech.modules.products;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.main.ErrorCode;
import me.kevinntech.modules.main.exception.DataNotFoundException;
import me.kevinntech.modules.main.exception.DuplicateDataException;
import me.kevinntech.modules.main.exception.NotValidArgumentException;
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
    public ResponseEntity save(@Valid @RequestBody ProductSaveRequestDto requestDto){
        // 상품 등록 처리
        Long savedId = productService.saveNewProduct(requestDto);

        return ResponseEntity.ok().build();
    }

    /*
    * 상품코드 중복 확인
    * */
    @PostMapping("/api/v1/products/validate")
    public ResponseEntity validate(@RequestBody Map<String, Object> param){
        String code = (String) param.get("code");
        boolean isDuplicated = productService.isDuplicate(code);

        if (isDuplicated)
            throw new DuplicateDataException(ErrorCode.DUPLICATION.getMessage());

        return ResponseEntity.ok().build();
    }

    /*
    * 상품 수정
    * */
    @PutMapping("/api/v1/products/{productId}")
    public ResponseEntity update(@PathVariable Long productId, @Valid @RequestBody ProductUpdateRequestDto requestDto){
        // 상품 수정 처리
        productService.updateProduct(productId, requestDto);

        return ResponseEntity.ok().build();
    }

    /*
    * 상품 삭제
    * */
    @DeleteMapping("/api/v1/products/{productId}")
    public ResponseEntity delete(@PathVariable Long productId){
        // 상품 삭제 처리
        Product deletedProduct = productService.deleteProduct(productId);

        if (deletedProduct == null)
            throw new DataNotFoundException(ErrorCode.NOT_FOUND_VALUE.getMessage());

        return ResponseEntity.ok().build();
    }

}
