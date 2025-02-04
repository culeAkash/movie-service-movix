package com.movix.movie.service.services.impl;

import com.movix.movie.service.dto.utils.MovieFilterDTO;
import com.movix.movie.service.entities.Genre;
import com.movix.movie.service.entities.Movie;
import com.movix.movie.service.entities.MovieGenres;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MovieSpecification {
    public static Specification<Movie> getSpecification(MovieFilterDTO movieFilterDTO) {
        return (root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();

            if(movieFilterDTO.getMovieName()!=null){
                predicates.add(criteriaBuilder.equal(root.get("movieName"),movieFilterDTO.getMovieName()));
            }

            if(movieFilterDTO.getDirector()!=null){
                predicates.add(criteriaBuilder.equal(root.get("director"),movieFilterDTO.getDirector()));
            }

            if(movieFilterDTO.getGenre()!=null){
                Join<MovieGenres, Movie> genresJoin = root.join(("movieId"));
                log.info(String.valueOf(genresJoin));
//                Join<Movie, Genre> genreDetailJoin = genresJoin.join(("movieGenres"));
                predicates.add(criteriaBuilder.equal(genresJoin.get("genreName"),movieFilterDTO.getGenre()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
