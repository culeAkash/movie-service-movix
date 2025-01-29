package com.movix.movie.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

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
    private Date releaseDate;
    private String synopsis;
    private Integer runtime;
    private String videoUrl;
    private String imageUrl;

    private List<GenreDTO> genres;
}
