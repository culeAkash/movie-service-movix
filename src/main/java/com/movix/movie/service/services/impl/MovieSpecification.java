package com.movix.movie.service.services.impl;

import com.movix.movie.service.dto.utils.MovieFilterDTO;
import com.movix.movie.service.entities.Movie;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

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
                predicates.add(criteriaBuilder.equal(root.get("genre"),movieFilterDTO.getGenre()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }
}
