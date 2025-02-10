package com.movix.movie.service.services;

import com.movix.movie.service.dto.utils.MovieInDTO;
import com.movix.movie.service.requests.MovieCreateRequest;
import com.movix.movie.service.responses.MovieResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface MovieService {
    public MovieResponse createMovie(MovieCreateRequest movieCreateRequest, MultipartFile posterFile);

    public String getPosterUnsignedUrl(String movieId);

    public Page<MovieResponse> getAllMovies(Pageable pageable);

    public void deleteMovie(String movieId);


    public Page<MovieResponse> searchMovieWithPaginationSortingAndFiltering(MovieInDTO movieInDTO);

    public MovieResponse getMovieById(String movieId);
}
