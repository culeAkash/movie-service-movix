package com.movix.movie.service.dto.utils;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieFilterDTO {
    private String movieName;
    private String director;
    private String genre;
    private LocalDate releaseDateFrom;
    private LocalDate releaseDateTo;
}
