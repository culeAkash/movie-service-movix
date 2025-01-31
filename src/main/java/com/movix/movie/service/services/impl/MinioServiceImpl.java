package com.movix.movie.service.services.impl;

import com.movix.movie.service.services.MinioService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.access.key}")
    private String accessKey;

    @Value("${minio.access.secret}")
    private String accessSecret;

    @Value("${minio.bucket.name}")
    private String bucketName;

    private final MinioClient minioClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(MinioServiceImpl.class);


    @Override
    public String uploadPosterFile(MultipartFile posterImageFile) {
        try {
            String objectName = "uploads/" + posterImageFile.getOriginalFilename();
            LOGGER.info("Minio client --> {}",minioClient);

            // upload file to minio
            try(InputStream inputStream = posterImageFile.getInputStream()) {
                this.minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .stream(inputStream,posterImageFile.getSize(),-1)
                                .contentType(posterImageFile.getContentType())
                                .build()
                );
            }
            return this.minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(1, TimeUnit.MINUTES)
                            .build()
            );
        }
        catch (Exception e) {
            LOGGER.error("MINIO_UPLOAD_SERVICE ---> {}", e.getMessage());
            throw new RuntimeException("Error Uploading File : " + e.getMessage(),e);
        }
    }

    @Override
    public String uploadVideoFile(MultipartFile videoImageFile) {
        return "";
    }
}
