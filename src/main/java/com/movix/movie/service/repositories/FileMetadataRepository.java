package com.movix.movie.service.repositories;

import com.movix.movie.service.entities.FileMetaData;
import com.movix.movie.service.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FileMetadataRepository extends JpaRepository<FileMetaData,String> {
    public Optional<FileMetaData> findByMovie_MovieId(String movieId);
}
