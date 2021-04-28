package me.kevinntech.modules.warehousing;

import lombok.RequiredArgsConstructor;
import me.kevinntech.modules.warehousing.dto.WarehousingSaveRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class WarehousingApiController {

    private final WarehousingService warehousingService;

    /*
    * 상품 입고 처리
    * */
    @PostMapping("/api/v1/warehousing")
    public ResponseEntity save(@Valid @RequestBody WarehousingSaveRequestDto requestDto, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        // 상품을 창고에 등록
        Warehousing savedWarehousing = warehousingService.saveNewWarehousing(requestDto);

        // 등록 실패 시, bad request
        if(savedWarehousing == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

}
