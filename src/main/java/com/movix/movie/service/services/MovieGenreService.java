package com.movix.movie.service.services;

import com.movix.movie.service.responses.GenreResponse;
import com.movix.movie.service.responses.MovieResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieGenreService {

    public List<MovieResponse> getMoviesByGenre(String genreName, Pageable pageable);

    public List<GenreResponse> getAllGenres();
}
