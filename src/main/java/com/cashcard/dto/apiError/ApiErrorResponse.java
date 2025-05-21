package com.cashcard.dto.apiError;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ApiErrorResponse {
    private HttpStatus status;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd H:m:s")
    private LocalDateTime timestamp;
    private List<Object> errors;
    private String instance;
    private String requestId;

    public void addFieldErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    public void addGlobalErrors(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    private void addValidationError(FieldError err) {
        this.addValidationError(err.getField(), err.getDefaultMessage(), err.getRejectedValue());
    }

    private void addValidationError(ObjectError err) {
        this.addValidationError(err.getObjectName(), err.getDefaultMessage());
    }

    private void addValidationError(String field, String message, Object rejectedValue) {
        ApiValidationErrorResponse validationErrorResponse = ApiValidationErrorResponse.builder()
                .field(field).message(message).rejectedValue(rejectedValue).build();
        this.addErrors(validationErrorResponse);
    }

    private void addValidationError(String field, String message) {
        ApiValidationErrorResponse validationErrorResponse = ApiValidationErrorResponse.builder()
                .field(field).message(message).build();
        this.addErrors(validationErrorResponse);
    }

    private void addErrors(ApiValidationErrorResponse validationErrorResponse) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(validationErrorResponse);
    }
}
