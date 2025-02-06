package com.movix.movie.service.services.impl;

import com.movix.movie.service.dto.GenreDTO;
import com.movix.movie.service.entities.Genre;
import com.movix.movie.service.exceptions.DuplicateEntryException;
import com.movix.movie.service.repositories.GenreRepository;
import com.movix.movie.service.requests.GenreCreateRequest;
import com.movix.movie.service.services.GenreService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreServiceImpl.class);

    @Override
    @Transactional(rollbackOn = DuplicateEntryException.class)
    public GenreDTO createNewGenre(GenreCreateRequest genreCreateRequest) throws DuplicateEntryException {
        // check for genre name as it must be unique and throw suitable exceptions during exception handling
       Optional<Genre> existingGenre =  this.genreRepository.findByGenreName(genreCreateRequest.getGenreName());
       if (existingGenre.isPresent()) {
           throw new DuplicateEntryException("Genre","Genre name",genreCreateRequest.getGenreName());
       }

        LOGGER.error(genreCreateRequest.getGenreName());
        Genre toCreateGenre = Genre.builder()
                .genreName(genreCreateRequest.getGenreName())
                .build();
        LOGGER.info(genreCreateRequest.getGenreName());
        Genre createdGenre = this.genreRepository.save(toCreateGenre);

        return this.modelMapper.map(createdGenre, GenreDTO.class);
    }
}
