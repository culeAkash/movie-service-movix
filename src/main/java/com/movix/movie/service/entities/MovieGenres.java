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

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id",nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id",nullable = false)
    private Genre genre;

    @Column(nullable = false)
    private Date timestamp;

    //This is a helper method to set the timestamp before saving the record
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        this.timestamp = new Date();
    }

}
