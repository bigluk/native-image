package com.luciano.nativeimage.exception.handler;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponseApi {

    private HttpStatus status;
    private String message;
    private String description;

}
