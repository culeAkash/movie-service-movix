package com.movix.movie.service.services.impl;

import com.movix.movie.service.dto.GenreDTO;
import com.movix.movie.service.entities.Genre;
import com.movix.movie.service.repositories.GenreRepository;
import com.movix.movie.service.requests.GenreCreateRequest;
import com.movix.movie.service.services.GenreService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final ModelMapper modelMapper;

    @Override
    public GenreDTO createNewGenre(GenreCreateRequest genreCreateRequest) {
        // check for genre name as it must be unique and throw suitable exceptions during exception handling
        Genre toCreateGenre = Genre.builder()
                .genreName(genreCreateRequest.getGenreName())
                .build();
        Genre createdGenre = this.genreRepository.save(toCreateGenre);

        return this.modelMapper.map(createdGenre, GenreDTO.class);
    }
}
