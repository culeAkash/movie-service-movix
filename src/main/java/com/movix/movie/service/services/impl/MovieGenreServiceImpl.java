package com.movix.movie.service.services.impl;

import com.movix.movie.service.entities.Genre;
import com.movix.movie.service.entities.Movie;
import com.movix.movie.service.entities.MovieGenres;
import com.movix.movie.service.exceptions.ResourceNotFoundException;
import com.movix.movie.service.repositories.GenreRepository;
import com.movix.movie.service.repositories.MovieGenresRepository;
import com.movix.movie.service.repositories.MovieRepository;
import com.movix.movie.service.responses.GenreResponse;
import com.movix.movie.service.responses.MovieResponse;
import com.movix.movie.service.services.MovieGenreService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class MovieGenreServiceImpl implements MovieGenreService {

    private final GenreRepository genreRepository;

    private final MovieGenresRepository movieGenresRepository;

    private final MovieRepository movieRepository;

    private final ModelMapper modelMapper;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<MovieResponse> getMoviesByGenre(String genreName, Pageable pageable) {
//        return null;
        // check for genre with the genre name
        log.info("Get all movies by genre name : {}", genreName);

        // If genre doesn't exist
        Genre existingGenre = this.genreRepository.findByGenreName(genreName)
                .orElseThrow(()-> new ResourceNotFoundException("Genre","Genre name",genreName));

        // get all movies for genre TODO : Have to check how Pageable works
        Page<MovieGenres> movieResponsePage = this.movieGenresRepository.findByGenre(existingGenre,pageable);

        List<MovieGenres> movieGenresList = movieResponsePage.getContent();

        List<MovieResponse> movieResponseList = new ArrayList<>();
        for(MovieGenres movieGenre : movieGenresList) {
           Movie movie =  this.movieRepository.findById(movieGenre.getMovie().getMovieId())
                   .orElseThrow(()->new ResourceNotFoundException("Movie","Movie id",movieGenre.getMovie().getMovieId()));
           movieResponseList.add(modelMapper.map(movie, MovieResponse.class));
        }

        return movieResponseList;
    }

    @Override
    public List<GenreResponse> getAllGenres() {
        // find all genres
        log.info("Get all genres");
        List<Genre> allGenres = this.genreRepository.findAll();
        List<GenreResponse> genreResponseList = new ArrayList<>();
        for(Genre genre : allGenres) {
           List<MovieGenres> movieGenresList = this.movieGenresRepository.findByGenre(genre);
           genreResponseList.add(GenreResponse.builder()
                           .genreId(genre.getGenreId())
                           .genreName(genre.getGenreName())
                           .movieCount((long) movieGenresList.size())
                   .build());
        }
        return genreResponseList;
    }
}
