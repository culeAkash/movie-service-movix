package com.movix.movie.service.controllers;

import com.movix.movie.service.dto.MovieDTO;
import com.movix.movie.service.requests.MovieCreateRequest;
import com.movix.movie.service.services.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RefreshScope
@RestController
@RequestMapping("/api/v1/movies")
@AllArgsConstructor
public class MovieController {

    private MovieService movieService;

    @PostMapping("/createMovie")
    public ResponseEntity<MovieDTO> createMovie(@RequestPart @Valid MovieCreateRequest movieCreateRequest,@RequestPart(required = false) MultipartFile posterFile,@RequestPart(required = false) MultipartFile videoFile) {
        MovieDTO movieDTO = movieService.createMovie(movieCreateRequest,posterFile);
        return ResponseEntity.ok(movieDTO);
    }
}
