package com.project.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "Response", description = "Schema to hold successful reponse information")
@AllArgsConstructor
@Getter
@Setter
public class ResponseDto {

    @Schema(description = "Status code in the response")
    private String statusCode;

    @Schema(description = "Status message in the response", example = "Request processed successfully")
    private String statusMsg;
}
