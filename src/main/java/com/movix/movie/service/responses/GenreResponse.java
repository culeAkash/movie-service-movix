package com.movix.movie.service.responses;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GenreResponse {
    private String genreId;
    private String genreName;
    private Long movieCount;
}
