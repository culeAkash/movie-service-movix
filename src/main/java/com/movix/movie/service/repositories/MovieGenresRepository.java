package com.movix.movie.service.repositories;

import com.movix.movie.service.entities.MovieGenres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieGenresRepository extends JpaRepository<MovieGenres,String> {
    public Page<MovieGenres> findByGenreId(String genreId, Pageable pageable);

    public List<MovieGenres> findByGenreIdContaining(String genreId);

    public List<MovieGenres> findByMovieId(String movieId);
}
