package com.movix.movie.service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieGenres {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String movieGenreId;

    @Column(name = "movie_id")
    private String movieId;

    @Column(name = "genre_id")
    private String genreId;

    @Column(nullable = false)
    private Date timestamp;

    //This is a helper method to set the timestamp before saving the record
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.timestamp = new Date();
    }

}
