package com.movix.movie.service.controllers;


import com.movix.movie.service.dto.GenreDTO;
import com.movix.movie.service.exceptions.DuplicateEntryException;
import com.movix.movie.service.requests.GenreCreateRequest;
import com.movix.movie.service.responses.APIResponse;
import com.movix.movie.service.responses.GenreResponse;
import com.movix.movie.service.responses.MovieResponse;
import com.movix.movie.service.services.GenreService;
import com.movix.movie.service.services.MovieGenreService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/api/v1/movieGenres")
@AllArgsConstructor
public class MovieGenreController {

    private GenreService genreService;

    private final MovieGenreService movieGenreService;

    @PostMapping("/createGenre")
    public ResponseEntity<APIResponse<GenreDTO>> createNewGenre(@RequestBody @Valid GenreCreateRequest genreCreateRequest) throws DuplicateEntryException {
       GenreDTO createdGenre = this.genreService.createNewGenre(genreCreateRequest);
        return new ResponseEntity<>(
                new APIResponse<>("success","Genre created successfully",HttpStatus.CREATED.value(), createdGenre),
                HttpStatus.CREATED);
    }

    @GetMapping("/getAllGenres")
    public ResponseEntity<APIResponse<List<GenreResponse>>> getAllGenres() {
        List<GenreResponse> genreResponseList = this.movieGenreService.getAllGenres();
        return ResponseEntity.ok().body(new APIResponse<>("success","Genres fetched successfully",HttpStatus.OK.value(), genreResponseList));
    }

    @GetMapping("/getMoviesOfGenre")
    public ResponseEntity<APIResponse<List<MovieResponse>>> getMoviesOfGenre(@RequestParam String genreName, Pageable pageable) {
        List<MovieResponse> movieResponses = this.movieGenreService.getMoviesByGenre(genreName, pageable);
        return new ResponseEntity<>(
                new APIResponse<>("success",String.format("Movies fetched of genre : %s",genreName),HttpStatus.OK.value(), movieResponses)
                , HttpStatus.OK);
    }
}
