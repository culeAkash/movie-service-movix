package com.movix.movie.service.controllers;

import com.movix.movie.service.dto.utils.MovieInDTO;
import com.movix.movie.service.requests.MovieCreateRequest;
import com.movix.movie.service.responses.APIResponse;
import com.movix.movie.service.responses.MovieResponse;
import com.movix.movie.service.responses.PageResponse;
import com.movix.movie.service.services.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/api/v1/movies")
@AllArgsConstructor
@Slf4j
public class MovieController {

    private MovieService movieService;

    @PostMapping(value = "/createMovie",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<APIResponse<MovieResponse>> createMovie(@RequestPart(value = "posterFile", required = false) MultipartFile posterFile
            , @RequestPart(value = "movieCreateRequest") MovieCreateRequest movieCreateRequest) {
        MovieResponse movieResponse = movieService.createMovie(movieCreateRequest,posterFile);
        APIResponse<MovieResponse> apiResponse = new APIResponse<>("success","Movie created successfully",HttpStatus.CREATED.value(),movieResponse);
        return new ResponseEntity<>(
                apiResponse
                ,HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}/poster")
    public ResponseEntity<String> getMovie(@PathVariable String movieId) {
//        String posterUrl = this.movieService.
        String posterUrl = this.movieService.getPosterUnsignedUrl(movieId);
        return ResponseEntity.ok(posterUrl);
    }

    @GetMapping("/getAllMovies")
    public ResponseEntity<PageResponse<List<MovieResponse>>> getAllMovies(Pageable pageable) {
        log.info("getAllMovies : {}",pageable.toString());
       Page<MovieResponse> movieResponsePage = this.movieService.getAllMovies(pageable);

        return getPageResponseResponseEntity(movieResponsePage);
    }

    @GetMapping("/filtering&pagination&sorting")
    public ResponseEntity<PageResponse<List<MovieResponse>>> getAllMovies(
            @RequestParam(name = "page",defaultValue = "0") Integer page,
            @RequestParam(name="size",defaultValue = "10") Integer size,
            @RequestParam(name = "sort",defaultValue = "[{\\\"field\\\":\\\"movieName\\\",\\\"direction\\\":\\\"desc\\\"}]") String sort,
            @RequestParam(name = "movie_name",required = false) String movieName,
            @RequestParam(name = "director",required = false) String director,
            @RequestParam(name="genre",required = false) String genre,
            @RequestParam(name = "releaseDateFrom",required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate releaseDateFrom,
            @RequestParam(name = "releaseDateTo",required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate releaseDateTo
            ) {
        Page<MovieResponse> movieResponsePage = this.movieService.searchMovieWithPaginationSortingAndFiltering(
                MovieInDTO.builder()
                        .movieName(movieName)
                        .director(director)
                        .genre(genre)
                        .releaseDateFrom(releaseDateFrom)
                        .releaseDateTo(releaseDateTo)
                        .page(page)
                        .size(size)
                        .sort(sort)
                        .build()
        );
        return getPageResponseResponseEntity(movieResponsePage);

    }

    @NotNull
    private ResponseEntity<PageResponse<List<MovieResponse>>> getPageResponseResponseEntity(Page<MovieResponse> movieResponsePage) {
        PageResponse<List<MovieResponse>> pageResponse = new PageResponse<>(
                movieResponsePage.getContent(),
                movieResponsePage.getTotalPages(),
                (int) (movieResponsePage.getTotalElements() + 1),
                movieResponsePage.getPageable().getPageSize(),
                (movieResponsePage.getPageable().getPageNumber() + 1),
                movieResponsePage.isLast(),
                movieResponsePage.isFirst()
        );
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteMovie")
    public ResponseEntity<APIResponse<Void>> deleteMovie(@RequestParam String movieId) {
        this.movieService.deleteMovie(movieId);
        return ResponseEntity.ok().body(
                new APIResponse<>(
                        "success",
                        "Movie deleted successfully",
                        HttpStatus.NO_CONTENT.value(),
                        null
                )
        );
    }


    @GetMapping("/getMovieById")
    public ResponseEntity<MovieResponse> getMovieById(@RequestParam String movieId) {
        MovieResponse movieResponse = this.movieService.getMovieById(movieId);
        return ResponseEntity.ok(movieResponse);
    }
}
