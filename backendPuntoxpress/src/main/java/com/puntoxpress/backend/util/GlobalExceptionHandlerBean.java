package com.puntoxpress.backend.util;

import com.puntoxpress.backend.model.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerBean {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        allErrors.forEach((error) -> {
            FieldError fieldError = (FieldError) error;
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        log.info("Error de validación: {}", errors);

        response.setData(errors);
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Error de validación");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return new ResponseEntity<>("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>("Recurso no encontrado", HttpStatus.NOT_FOUND);
    }
}