package me.kevinntech.modules.main;

import me.kevinntech.modules.main.exception.DataNotFoundException;
import me.kevinntech.modules.main.exception.DuplicateDataException;
import me.kevinntech.modules.main.exception.NotValidArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotValidArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleNotValidArgument(NotValidArgumentException ex) {
        ApiErrorResponse response = new ApiErrorResponse("error-00001", "전달받은 데이터가 유효하지 않습니다.");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleDataNotFound(DataNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse("error-00002", "존재하지 않는 데이터입니다.");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateData(DuplicateDataException ex) {
        ApiErrorResponse response = new ApiErrorResponse("error-00003", "중복된 데이터입니다.");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
