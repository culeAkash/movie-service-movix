package com.movix.movie.service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "genre_id")
    private String genreId;

    @Column(name = "genre_name",nullable = false,unique = true)
    private String genreName;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "genre_id", referencedColumnName = "genre_id")
    private Set<MovieGenres> movieGenres = new HashSet<MovieGenres>();
}
