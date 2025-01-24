package com.movix.movie.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GenreDTO {
    private String genreId;
    private String genreName;
}
