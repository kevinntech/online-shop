package me.kevinntech.modules.main;

import lombok.extern.slf4j.Slf4j;
import me.kevinntech.modules.main.exception.DataNotFoundException;
import me.kevinntech.modules.main.exception.DuplicateDataException;
import me.kevinntech.modules.main.exception.NotValidArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException", ex);
        ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, ex.getBindingResult());

        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.INVALID_INPUT_VALUE.getStatus()));
    }

    @ExceptionHandler(NotValidArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleNotValidArgument(NotValidArgumentException ex) {
        log.error("handleNotValidArgument", ex);
        ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);

        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.INVALID_INPUT_VALUE.getStatus()));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleDataNotFound(DataNotFoundException ex) {
        log.error("DataNotFoundException", ex);
        ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.NOT_FOUND_VALUE);

        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.NOT_FOUND_VALUE.getStatus()));
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateData(DuplicateDataException ex) {
        log.error("DuplicateDataException", ex);
        ApiErrorResponse response = ApiErrorResponse.of(ErrorCode.DUPLICATION);

        return new ResponseEntity<>(response, HttpStatus.valueOf(ErrorCode.DUPLICATION.getStatus()));
    }

}
