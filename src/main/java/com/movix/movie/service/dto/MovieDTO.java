package com.movix.movie.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MovieDTO {
    private String movieId;
    private String movieName;
    private String director;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private String synopsis;
    private Integer runtime;

    private List<GenreDTO> genres;
}
