package com.movix.movie.service.services;

import com.movix.movie.service.dto.MovieDTO;
import com.movix.movie.service.requests.MovieCreateRequest;

public interface MovieService {
    public MovieDTO createMovie(MovieCreateRequest movieCreateRequest);
}
