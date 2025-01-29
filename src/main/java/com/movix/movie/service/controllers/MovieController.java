package com.movix.movie.service.controllers;

import com.movix.movie.service.dto.MovieDTO;
import com.movix.movie.service.requests.MovieCreateRequest;
import com.movix.movie.service.services.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/api/v1/movies")
@AllArgsConstructor
public class MovieController {

    private MovieService movieService;

    @PostMapping("/createMovie")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody @Valid MovieCreateRequest movieCreateRequest) {
        MovieDTO movieDTO = movieService.createMovie(movieCreateRequest);
        return ResponseEntity.ok(movieDTO);
    }
}
