package com.movix.movie.service.dto.utils;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieFilterDTO {
    private String movieName;
    private String director;
    private String genre;
}
