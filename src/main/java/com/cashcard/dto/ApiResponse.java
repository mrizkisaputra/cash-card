package com.cashcard.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data @Builder
public class ApiResponse {
    private HttpStatus status;
    private String message;
    private Object data;
    private Pagination pagination;
}
