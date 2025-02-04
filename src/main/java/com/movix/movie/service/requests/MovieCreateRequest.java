package com.movix.movie.service.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MovieCreateRequest {
    @NotBlank(message = "Movie name must not be blank")
    private String movieName;
    @NotBlank(message = "Movie name must not be blank")
    private String director;
    private String synopsis;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private Integer runtime;
    private String genre;
}
