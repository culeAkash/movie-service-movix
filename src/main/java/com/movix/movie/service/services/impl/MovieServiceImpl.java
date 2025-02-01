package com.movix.movie.service.services.impl;

import com.movix.movie.service.dto.MovieDTO;
import com.movix.movie.service.entities.FileMetaData;
import com.movix.movie.service.entities.Movie;
import com.movix.movie.service.repositories.FileMetadataRepository;
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

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    private final MinioService minioService;

    private final FileMetadataRepository fileMetadataRepository;


    @Override
    @Transactional
    public MovieDTO createMovie(MovieCreateRequest movieCreateRequest, MultipartFile posterFile) {
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
            return this.modelMapper.map(savedMovie, MovieDTO.class);
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
}
