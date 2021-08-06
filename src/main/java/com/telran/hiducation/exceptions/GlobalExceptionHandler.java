package com.telran.hiducation.exceptions;

import com.telran.hiducation.pojo.dto.ErrorDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDto<Map<String, String>> errorMessageDto = ErrorDto.<Map<String, String>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_GATEWAY.getReasonPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getBindingResult().getAllErrors()
                        .stream()
                        .collect(Collectors.toMap(e -> (
                                        (e instanceof FieldError) ? ((FieldError) e).getField() : e.getObjectName()),
                                DefaultMessageSourceResolvable::getDefaultMessage))

                        )
                .build();
        return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorDto<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        return ErrorDto.<Map<String, String>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getConstraintViolations()
                        .stream()
                        .collect(Collectors.toMap(cv -> cv.getPropertyPath().toString().replaceAll("^.*\\.", ""),
                                ConstraintViolation::getMessage))
                )
                .build();
    }

    @ExceptionHandler(value = {
//            IllegalArgumentException.class,
//            ContactNotFoundException.class,
//            ContactAlreadyExistException.class,
            RuntimeException.class
    })
    public ErrorDto<String> handleCustomException(RuntimeException ex, WebRequest request) {
        return ErrorDto.<String>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getMessage())
                .build();
    }



    @ExceptionHandler(value = {
            UsernameNotFoundException.class,
    })
    public ResponseEntity<Object> handleNotFoundException(AuthenticationException ex, WebRequest request) {
        ErrorDto<String> errorDto = ErrorDto.<String>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {
            UsernameDuplicateException.class,
            DuplicateExceptionDto.class
    })
    public ErrorDto<String> handleCustomConflictException(RuntimeException ex, WebRequest request) {
        return ErrorDto.<String>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .message(ex.getMessage())
                .build();
    }

}
