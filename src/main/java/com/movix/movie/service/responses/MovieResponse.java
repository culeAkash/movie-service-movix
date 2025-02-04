package com.movix.movie.service.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.movix.movie.service.dto.GenreDTO;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieResponse {
    private String movieId;
    private String movieName;
    private String director;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private String synopsis;
    private Integer runtime;

    private String posterUrl;

    private List<GenreDTO> genres;
}
