package com.movix.movie.service.controllers;

import com.movix.movie.service.dto.MovieDTO;
import com.movix.movie.service.requests.MovieCreateRequest;
import com.movix.movie.service.responses.MovieResponse;
import com.movix.movie.service.services.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/api/v1/movies")
@AllArgsConstructor
public class MovieController {

    private MovieService movieService;

    @PostMapping("/createMovie")
    public ResponseEntity<MovieDTO> createMovie(@RequestPart @Valid MovieCreateRequest movieCreateRequest,@RequestPart(required = false) MultipartFile posterFile) {
        MovieDTO movieDTO = movieService.createMovie(movieCreateRequest,posterFile);
        return ResponseEntity.ok(movieDTO);
    }

    @GetMapping("/{movieId}/poster")
    public ResponseEntity<String> getMovie(@PathVariable String movieId) {
//        String posterUrl = this.movieService.
        String posterUrl = this.movieService.getPosterUnsignedUrl(movieId);
        return ResponseEntity.ok(posterUrl);
    }

    @GetMapping("/getAllMovies")
    public ResponseEntity<Page<MovieResponse>> getAllMovies(
            @RequestParam(name = "page",defaultValue = "0") Integer page,
            @RequestParam(name="size",defaultValue = "10") Integer size,
            @RequestParam(name = "sort",defaultValue = "[{\\\"field\\\":\\\"movieName\\\",\\\"direction\\\":\\\"desc\\\"}]") String sort,
            @RequestParam(name = "movie_name",required = false) String movieName,
            @RequestParam(name = "director",required = false) String director,
            @RequestParam(name="genre",required = false) String genre
            ) {
//        Page<MovieResponse> movieDTOPage = this.movieService.
        // TODO : Complete Pagination for Movie Service
    }
}
