package com.project.accounts.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "ErrorResponse", description = "Schema to hold error response information")
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponseDto {

    @Schema(description = "API path invoked by client")
    private String apiPath;

    @Schema(description = "Error code representing error happened")
    private HttpStatus errorCode;

    @Schema(description = "Error message represeting error happened")
    private String errorMsg;

    @Schema(description = "Time representing when error happened")
    private LocalDateTime errorTime;

}
