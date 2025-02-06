package com.movix.movie.service.repositories;

import com.movix.movie.service.entities.Genre;
import com.movix.movie.service.entities.MovieGenres;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieGenresRepository extends JpaRepository<MovieGenres,String> {
    public Page<MovieGenres> findByGenre(Genre genre, Pageable pageable);

    public List<MovieGenres> findByGenre(Genre genre);

}
