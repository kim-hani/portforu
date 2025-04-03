package org.pinggu.portforu.config;

import org.pinggu.portforu.common.dto.ErrorResponse;
import org.pinggu.portforu.common.exception.CustomException;
import org.pinggu.portforu.common.exception.ServerException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 기본 Custom예외처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> invalidRequestExceptionException(CustomException e) {
        return getErrorResponse(e.getStatus(), e.getMessage());
    }

    // 서버 예외처리
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorResponse> handleServerException(ServerException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return getErrorResponse(status, e.getMessage());
    }

    // 유효성 검사 예외처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Invalid request parameters");
        return getErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    // 공통 응답 부분
    private ResponseEntity<ErrorResponse> getErrorResponse(HttpStatus status, String message) {
        ErrorResponse response = new ErrorResponse(
                status.name(),
                status.value(),
                message,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, status);
    }
}
