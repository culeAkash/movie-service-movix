package com.movix.movie.service.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreCreateRequest {
    @NotBlank(message = "Genre name must not be blank")
    private String genreName;
}
