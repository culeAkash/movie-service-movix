package com.movix.movie.service.controllers;

import com.movix.movie.service.dto.utils.MovieInDTO;
import com.movix.movie.service.requests.MovieCreateRequest;
import com.movix.movie.service.responses.MovieResponse;
import com.movix.movie.service.services.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RefreshScope
@RestController
@RequestMapping("/api/v1/movies")
@AllArgsConstructor
@Slf4j
public class MovieController {

    private MovieService movieService;

    @PostMapping(value = "/createMovie",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MovieResponse> createMovie(@RequestPart(value = "posterFile", required = false) MultipartFile posterFile
            ,@RequestPart(value = "movieCreateRequest") MovieCreateRequest movieCreateRequest) {
        MovieResponse movieResponse = movieService.createMovie(movieCreateRequest,posterFile);
        return ResponseEntity.ok(movieResponse);
    }

    @GetMapping("/{movieId}/poster")
    public ResponseEntity<String> getMovie(@PathVariable String movieId) {
//        String posterUrl = this.movieService.
        String posterUrl = this.movieService.getPosterUnsignedUrl(movieId);
        return ResponseEntity.ok(posterUrl);
    }

    @GetMapping("/getAllMovies")
    public ResponseEntity<Page<MovieResponse>> getAllMovies(Pageable pageable) {
        log.info("getAllMovies : {}",pageable.toString());
       Page<MovieResponse> movieResponsePage = this.movieService.getAllMovies(pageable);
       return ResponseEntity.ok(movieResponsePage);
    }

    @GetMapping("/filtering&pagination&sorting")
    public ResponseEntity<Page<MovieResponse>> getAllMovies(
            @RequestParam(name = "page",defaultValue = "0") Integer page,
            @RequestParam(name="size",defaultValue = "10") Integer size,
            @RequestParam(name = "sort",defaultValue = "[{\\\"field\\\":\\\"movieName\\\",\\\"direction\\\":\\\"desc\\\"}]") String sort,
            @RequestParam(name = "movie_name",required = false) String movieName,
            @RequestParam(name = "director",required = false) String director,
            @RequestParam(name="genre",required = false) String genre
            ) {
        Page<MovieResponse> movieResponsePage = this.movieService.searchMovieWithPaginationSortingAndFiltering(
                MovieInDTO.builder()
                        .movieName(movieName)
                        .director(director)
                        .genre(genre)
                        .page(page)
                        .size(size)
                        .sort(sort)
                        .build()
        );
        return ResponseEntity.ok(movieResponsePage);
    }

    @DeleteMapping("/deleteMovie")
    public ResponseEntity<Void> deleteMovie(@RequestParam String movieId) {
        this.movieService.deleteMovie(movieId);
        return ResponseEntity.noContent().build();
    }
}
