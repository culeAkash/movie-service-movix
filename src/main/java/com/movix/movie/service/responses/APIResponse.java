package com.movix.movie.service.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.PostConstruct;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse<T> {
    private String status;
    private String message;
    private int httpStatus;
    private T data;

    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();


    // Explicit constructor for manual object creation
    public APIResponse(String status, String message, int httpStatus, T data) {
        this.status = status;
        this.message = message;
        this.httpStatus = httpStatus;
        this.data = data;
        this.timestamp = LocalDateTime.now(); // Ensure timestamp is always set
    }


    // Ensure timestamp is always set
    @PostConstruct
    private void init() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}
