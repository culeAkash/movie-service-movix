package com.movix.movie.service.repositories;

import com.movix.movie.service.entities.Movie;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,String>, JpaSpecificationExecutor<Movie> {
    public Movie findByMovieName(String movieName);

    public List<Movie> findAllByDirector(String director);

//    public String findBy
    @NonNull
    public List<Movie> findAll(Specification<Movie> specification);
}
