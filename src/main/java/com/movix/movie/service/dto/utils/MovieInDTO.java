package com.movix.movie.service.dto.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieInDTO {
    private String movieId;
    private String movieName;
    private String director;
    private String genre;

    private Integer page;
    private Integer size;
    private String sort;
}
