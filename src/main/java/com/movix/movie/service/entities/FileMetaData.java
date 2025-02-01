package com.movix.movie.service.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file_metadata")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileMetaData {
    @Id
    @Column(name = "file_id")
    private String fileId;

    private Long size;

    private String httpContentType;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id",referencedColumnName = "movie_id")
    Movie movie;
}
