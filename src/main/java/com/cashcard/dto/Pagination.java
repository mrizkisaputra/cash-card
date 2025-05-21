package com.cashcard.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Pagination {
    private Long totalElement;
    private Integer totalPage;
    private Integer size;
}
