package com.movix.movie.service.services;

import com.movix.movie.service.dto.GenreDTO;
import com.movix.movie.service.requests.GenreCreateRequest;


public interface GenreService {
    public GenreDTO createNewGenre(GenreCreateRequest genreCreateRequest);
}
