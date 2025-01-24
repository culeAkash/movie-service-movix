package com.movix.movie.service.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreCreateRequest {
    private String genreName;
}
