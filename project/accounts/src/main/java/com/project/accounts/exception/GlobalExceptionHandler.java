package com.project.accounts.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.project.accounts.dto.ErrorResponseDto;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
                HashMap<String, String> validationErrors = new HashMap<>();
                List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();
                validationErrorList.forEach((error) -> {
                        String fieldName = ((FieldError) error).getField();
                        String validationMsg = error.getDefaultMessage();
                        validationErrors.put(fieldName, validationMsg);
                });
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
        }

        @ExceptionHandler(CustomerAlreadyExistsException.class)
        public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistException(
                        CustomerAlreadyExistsException exception,
                        HttpServletRequest request) {
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(new ErrorResponseDto(request.getRequestURI(),
                                                HttpStatus.BAD_REQUEST,
                                                exception.getMessage(),
                                                LocalDateTime.now()));
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                        HttpServletRequest request) {
                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(new ErrorResponseDto(request.getRequestURI(),
                                                HttpStatus.NOT_FOUND,
                                                exception.getMessage(),
                                                LocalDateTime.now()));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, HttpServletRequest request) {
                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ErrorResponseDto(request.getRequestURI(),
                                                HttpStatus.INTERNAL_SERVER_ERROR,
                                                exception.getMessage(),
                                                LocalDateTime.now()));
        }
}
