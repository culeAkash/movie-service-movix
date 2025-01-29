package com.movix.movie.service.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private Date releaseDate;
    private Integer runtime;
}
