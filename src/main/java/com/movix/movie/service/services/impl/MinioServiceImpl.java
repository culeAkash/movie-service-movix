package com.movix.movie.service.services.impl;

import com.movix.movie.service.exceptions.GenericException;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;
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
    public void uploadPosterFile(MultipartFile posterImageFile, UUID fileUuid) {
        try {
//            String objectName = "uploads/posters/" + posterImageFile.getOriginalFilename();
            LOGGER.info("Minio client --> {}",minioClient);

            // upload file to minio
            try(InputStream inputStream = posterImageFile.getInputStream()) {
                this.minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(fileUuid.toString())
                                .stream(inputStream,posterImageFile.getSize(),-1)
                                .contentType(posterImageFile.getContentType())
                                .build()
                );
            }
        }
        catch (Exception e) {
            LOGGER.error("MINIO_UPLOAD_SERVICE ---> {}", e.getMessage());
            throw new GenericException("Error uploading posterFile : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getMoviePosterUrl(UUID fileUuid) {
        try{
            return this.minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(fileUuid.toString())
                        .expiry(22,TimeUnit.HOURS)
                        .build()
            );
        }catch(Exception e){
            LOGGER.error("MINIO_GET_POST_SERVICE ---> {}", e.getMessage());
            throw new RuntimeException("Error Getting Movie Poster",e);
        }
    }

    @Override
    public String uploadVideoFile(MultipartFile videoImageFile) {
        return "";
    }
}
