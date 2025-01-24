package com.movix.movie.service.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "movie_id")
    private String movieId;

    @Column(name = "movie_name",nullable = false)
    private String movieName;

    @Column(name = "movie_director",nullable = false, length = 50)
    private String director;

    @Column(name = "release_date",nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date releaseDate;

    private Integer runtime;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "poster_url")
    private String posterUrl;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")
    private Set<MovieGenres> movieGenres = new HashSet<>();

}
