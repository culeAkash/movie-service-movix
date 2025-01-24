package com.movix.movie.service.repositories;

import com.movix.movie.service.entities.MovieGenres;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieGenresRepository extends JpaRepository<MovieGenres,String> {
    public List<MovieGenres> findByGenreId(String genreId);

    public List<MovieGenres> findByGenreIdContaining(String genreId);

    public List<MovieGenres> findByMovieId(String movieId);
}
