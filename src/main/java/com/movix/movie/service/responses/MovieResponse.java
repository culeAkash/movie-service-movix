package com.movix.movie.service.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.movix.movie.service.dto.GenreDTO;
import com.movix.movie.service.dto.MovieDTO;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MovieResponse {
    private String movieId;
    private String movieName;
    private String director;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date releaseDate;
    private String synopsis;
    private Integer runtime;

    private String posterUrl;

    private List<GenreDTO> genres;
}
