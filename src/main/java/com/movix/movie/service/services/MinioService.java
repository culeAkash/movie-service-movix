package com.movix.movie.service.services;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    public String uploadPosterFile(MultipartFile posterImageFile);

    public String uploadVideoFile(MultipartFile videoImageFile);
}
