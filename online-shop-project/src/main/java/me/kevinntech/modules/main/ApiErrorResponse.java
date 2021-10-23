package me.kevinntech.modules.main;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {

    // 에러에 대한 고유 식별자
    private String code;

    // 에러에 대해 사람이 읽을 수 있는 간단한 메세지
    private String message;

}
