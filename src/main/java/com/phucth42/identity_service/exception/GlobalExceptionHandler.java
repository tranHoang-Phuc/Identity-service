package com.phucth42.identity_service.exception;

import com.phucth42.identity_service.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse response = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e) {
        ApiResponse response = ApiResponse.builder()
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);

            var constraint = e.getBindingResult().getAllErrors()
                    .getFirst().unwrap(ConstraintViolation.class);
            attributes = constraint.getConstraintDescriptor().getAttributes();

        } catch (IllegalArgumentException exception) {

        }
        ApiResponse response = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(Objects.nonNull(attributes) ?
                        mapAttribute(errorCode.getMessage(), attributes)
                        : errorCode.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            message = message.replace("{" + entry.getKey() + "}", entry.getValue().toString());
        }
        return message;
    }
}
