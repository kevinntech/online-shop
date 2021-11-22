package me.kevinntech.modules.main;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiErrorResponse {

    private int status;

    private String code; // 에러에 대한 고유 식별자

    private String message; // 에러에 대해 사람이 읽을 수 있는 간단한 메세지

    private List<FieldError> errors;

    private LocalDateTime timestamp;

    private ApiErrorResponse(ErrorCode code) {
        this.status = code.getStatus();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.errors = new ArrayList<>();
        this.code = code.getCode();
        this.timestamp = LocalDateTime.now();
    }

    private ApiErrorResponse(ErrorCode code, List<FieldError> errors) {
        this.status = code.getStatus();
        this.code = code.getCode();
        this.message = code.getMessage();
        this.errors = errors;
        this.code = code.getCode();
        this.timestamp = LocalDateTime.now();
    }

    public static ApiErrorResponse of(ErrorCode code) {
        return new ApiErrorResponse(code);
    }

    public static ApiErrorResponse of(ErrorCode code, BindingResult bindingResult) {
        return new ApiErrorResponse(code, FieldError.of(bindingResult));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            return bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> new FieldError(
                                    error.getField(),
                                    error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                                    error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
