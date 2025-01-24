package com.movix.movie.service.controllers;


import com.movix.movie.service.dto.GenreDTO;
import com.movix.movie.service.requests.GenreCreateRequest;
import com.movix.movie.service.services.GenreService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/api/v1/movieGenres")
@AllArgsConstructor
public class MovieGenreController {

    private GenreService genreService;

    public ResponseEntity<GenreDTO> createNewGenre(GenreCreateRequest genreCreateRequest) {
       GenreDTO createdGenre = this.genreService.createNewGenre(genreCreateRequest);
       return new ResponseEntity<GenreDTO>(createdGenre, HttpStatus.CREATED);
    }
}
