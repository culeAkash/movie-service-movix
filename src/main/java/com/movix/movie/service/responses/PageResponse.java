package com.movix.movie.service.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private T data;
    private int totalPages;
    private int totalElements;
    private int pageSize;
    private int pageNumber;
    private boolean isLastPage;
    private boolean isFirstPage;
}
