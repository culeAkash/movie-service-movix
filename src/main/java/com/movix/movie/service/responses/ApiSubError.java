package com.movix.movie.service.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSubError {
    private String resource;
    private String field;
    private Object rejectedValue;
    private String message;
}
