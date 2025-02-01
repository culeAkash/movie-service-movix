package com.movix.movie.service.services;

import com.movix.movie.service.dto.MovieDTO;
import com.movix.movie.service.requests.MovieCreateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface MovieService {
    public MovieDTO createMovie(MovieCreateRequest movieCreateRequest, MultipartFile posterFile);

    public String getPosterUnsignedUrl(String movieId);
}
