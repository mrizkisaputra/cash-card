package com.cashcard;

import com.cashcard.dto.apiError.ApiErrorResponse;
import com.cashcard.dto.apiError.ApiValidationErrorResponse;
import com.cashcard.filters.RequestIdFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RestExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResponseStatusException.class})
    protected ResponseEntity<Object> handleResponseStatus(ResponseStatusException ex, HttpServletRequest request) {
        ApiErrorResponse apiError = ApiErrorResponse.builder()
                .status((HttpStatus) ex.getStatusCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .instance(request.getRequestURI())
                .requestId(request.getAttribute(RequestIdFilter.REQUEST_ID).toString())
                .build();

        // ✅ Logging error response
        log.warn("RequestId: {}, IPAddress:{}, Path: {}, Method: {}, Causes={}",
                request.getAttribute(RequestIdFilter.REQUEST_ID),
                request.getRemoteAddr(),
                request.getRequestURI(),
                request.getMethod(),
                ex.getLocalizedMessage()
        );

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
        HttpServletRequest servletRequest = servletWebRequest.getRequest();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        ApiErrorResponse apiError = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation Failed")
                .timestamp(LocalDateTime.now())
                .instance(servletRequest.getRequestURI())
                .requestId(servletRequest.getAttribute(RequestIdFilter.REQUEST_ID).toString())
                .build();
        apiError.addFieldErrors(fieldErrors);
        apiError.addGlobalErrors(globalErrors);

        // ✅ Logging error response
        log.warn("RequestId: {}, IPAddress:{}, Path: {}, Method: {}, Causes={}",
                servletRequest.getAttribute(RequestIdFilter.REQUEST_ID),
                servletRequest.getRemoteAddr(),
                servletRequest.getRequestURI(),
                servletRequest.getMethod(),
                ex.getLocalizedMessage()
        );

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
