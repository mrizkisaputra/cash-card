package com.cashcard.dto.apiError;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ApiValidationErrorResponse {
    private String field;
    private Object rejectedValue;
    private String message;
}
