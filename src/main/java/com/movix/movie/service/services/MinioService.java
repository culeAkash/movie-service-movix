package com.movix.movie.service.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface MinioService {
    public void uploadPosterFile(MultipartFile posterImageFile, UUID fileUuid);

    public String getMoviePosterUrl(UUID fileUuid);

    public String uploadVideoFile(MultipartFile videoImageFile);
}
