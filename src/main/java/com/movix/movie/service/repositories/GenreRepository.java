package com.movix.movie.service.repositories;

import com.movix.movie.service.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
    public Optional<Genre> findByGenreName(String genreName);
}
