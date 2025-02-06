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
                Join<Movie, MovieGenres> genresJoin = root.join(("movieGenres"));
//                log.info(String.valueOf(genresJoin));
                Join<MovieGenres, Genre> genreDetailJoin = genresJoin.join(("genre"));
                predicates.add(criteriaBuilder.equal(genreDetailJoin.get("genreName"),movieFilterDTO.getGenre()));
            }

            if(movieFilterDTO.getReleaseDateFrom()!=null && movieFilterDTO.getReleaseDateTo()!=null){
                predicates.add(criteriaBuilder.between(root.get("releaseDate"),movieFilterDTO.getReleaseDateFrom(),movieFilterDTO.getReleaseDateTo()));
            }
            else if(movieFilterDTO.getReleaseDateFrom()!=null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("releaseDate"),movieFilterDTO.getReleaseDateFrom()));
            }
            else if(movieFilterDTO.getReleaseDateTo()!=null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("releaseDate"),movieFilterDTO.getReleaseDateTo()));
            }

            //            log.info("Final Criteria : {}",finalCriteria.getExpressions().toString());
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
