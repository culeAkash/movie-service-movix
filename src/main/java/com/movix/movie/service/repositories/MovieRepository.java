package com.movix.movie.service.repositories;

import com.movix.movie.service.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,String> {
    public Movie findByMovieName(String movieName);

    public List<Movie> findAllByDirector(String director);
}
