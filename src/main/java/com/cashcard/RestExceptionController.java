package com.cashcard;

import com.cashcard.dto.apiError.ApiErrorResponse;
import com.cashcard.dto.apiError.ApiValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class RestExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResponseStatusException.class})
    protected ResponseEntity<Object> handleResponseStatus(ResponseStatusException ex, HttpServletRequest request) {
        ApiErrorResponse apiError = ApiErrorResponse.builder()
                .status((HttpStatus) ex.getStatusCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .instance(request.getRequestURI())
                .build();
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        ApiErrorResponse apiError = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation Failed")
                .timestamp(LocalDateTime.now())
                .instance(servletWebRequest.getRequest().getRequestURI())
                .build();
        apiError.addFieldErrors(fieldErrors);
        apiError.addGlobalErrors(globalErrors);
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
