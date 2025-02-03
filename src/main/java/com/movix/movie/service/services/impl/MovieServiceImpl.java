package com.movix.movie.service.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movix.movie.service.dto.utils.MovieFilterDTO;
import com.movix.movie.service.dto.utils.MovieInDTO;
import com.movix.movie.service.dto.utils.MovieSortDTO;
import com.movix.movie.service.entities.FileMetaData;
import com.movix.movie.service.entities.Movie;
import com.movix.movie.service.repositories.FileMetadataRepository;
import com.movix.movie.service.repositories.MovieRepository;
import com.movix.movie.service.requests.MovieCreateRequest;
import com.movix.movie.service.responses.MovieResponse;
import com.movix.movie.service.services.MinioService;
import com.movix.movie.service.services.MovieService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    private final MinioService minioService;

    private final FileMetadataRepository fileMetadataRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);


    @Override
    @Transactional
    public MovieResponse createMovie(MovieCreateRequest movieCreateRequest, MultipartFile posterFile) {
        try {
            UUID fileUuid = UUID.randomUUID();

            Movie toSaveMovie = Movie.builder()
                    .movieName(movieCreateRequest.getMovieName())
                    .director(movieCreateRequest.getDirector())
                    .runtime(movieCreateRequest.getRuntime())
                    .releaseDate(movieCreateRequest.getReleaseDate())
                    .build();
            Movie savedMovie = this.movieRepository.save(toSaveMovie);

            FileMetaData fileMetaData = FileMetaData.builder()
                    .fileId(fileUuid.toString())
                    .size(posterFile.getSize())
                    .httpContentType(posterFile.getContentType())
                    .movie(savedMovie)
                    .build();
            FileMetaData savedFileMetadata = fileMetadataRepository.save(fileMetaData);
            this.minioService.uploadPosterFile(posterFile, fileUuid);
            return this.modelMapper.map(savedMovie, MovieResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public String getPosterUnsignedUrl(String movieId) {
        try{
            FileMetaData fileMetaData = fileMetadataRepository.findByMovie_MovieId(movieId).orElse(null);
            //TODO : check for resource exception
            assert fileMetaData != null;
            return this.minioService.getMoviePosterUrl(UUID.fromString(fileMetaData.getFileId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<MovieResponse> getAllMovies(Pageable pageable) {
       Page<Movie> movies =  this.movieRepository.findAll(pageable);
       return movies.map(movie -> this.modelMapper.map(movie, MovieResponse.class));
    }

    @Override
    public void deleteMovie(String movieId) {
        //TODO : Check for exceptions if movie id exists
        // TODO : put to queue as reviews and ratings must also be deleted
        this.movieRepository.deleteById(movieId);
    }

    @Override
    public Page<MovieResponse> searchMovieWithPaginationSortingAndFiltering(MovieInDTO movieInDTO) {
        // Create Filter DTO
        MovieFilterDTO movieFilterDTO = MovieFilterDTO.builder()
                .movieName(movieInDTO.getMovieName())
                .director(movieInDTO.getDirector())
                .genre(movieInDTO.getGenre())
                .build();

        // Parse and create sort orders
        List<MovieSortDTO> movieSortDTOS = jsonStringToSortDto(movieInDTO.getSort());
        List<Sort.Order> orders = new ArrayList<>();

        if(movieSortDTOS!=null){
            for(MovieSortDTO movieSortDTO : movieSortDTOS){
                Sort.Direction direction = Objects.equals(movieSortDTO.getDirection(), "desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                orders.add(new Sort.Order(direction,movieSortDTO.getField()));
            }
        }

        // create page request with sorting
        PageRequest pageRequest = PageRequest.of(
                movieInDTO.getPage(),
                movieInDTO.getSize(),
                Sort.by(orders)
        );

        // Apply specification and pagination
        Specification<Movie> specification = MovieSpecification.getSpecification(movieFilterDTO);
        Page<Movie> movies = this.movieRepository.findAll(specification, pageRequest);

        return movies.map((movie)->modelMapper.map(movie, MovieResponse.class));
    }

    private List<MovieSortDTO> jsonStringToSortDto(String jsonString) {
        try {
            ObjectMapper obj = new ObjectMapper();
            return obj.readValue(jsonString, new TypeReference<>() {});
        } catch (Exception e) {
            LOGGER.info("Exception : {}", e.getMessage());
            return null;
        }
    }
}
