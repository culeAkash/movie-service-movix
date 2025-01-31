package com.movix.movie.service.services.impl;

import com.movix.movie.service.dto.MovieDTO;
import com.movix.movie.service.entities.Movie;
import com.movix.movie.service.repositories.MovieRepository;
import com.movix.movie.service.requests.MovieCreateRequest;
import com.movix.movie.service.services.MinioService;
import com.movix.movie.service.services.MovieService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    private final MinioService minioService;


    @Override
    public MovieDTO createMovie(MovieCreateRequest movieCreateRequest, MultipartFile posterFile) {
        String posterUrl = this.minioService.uploadPosterFile(posterFile);

        Movie toSaveMovie = Movie.builder()
                .synopsis(movieCreateRequest.getSynopsis())
                .movieName(movieCreateRequest.getMovieName())
                .releaseDate(movieCreateRequest.getReleaseDate())
                .director(movieCreateRequest.getDirector())
                .runtime(movieCreateRequest.getRuntime())
                .posterUrl(posterUrl)
                .build();
        Movie savedMovie =  this.movieRepository.save(toSaveMovie);
       return this.modelMapper.map(savedMovie, MovieDTO.class);
    }
}
