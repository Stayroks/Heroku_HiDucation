package com.telran.hiducation.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "Error", description = "Error model for all errors")
public class ErrorDto<T> {

    @Schema(description = "The error date-time",
            example = "2021-06-17T19:18:02.278+0000")
    private LocalDateTime timestamp;

    @Schema(description = "The error http code",
            example = "404")
    private int status;

    @Schema(description = "Description of the reason",
            example = "Not Found")
    private String error;

    @Schema(description = "The error message",
            example = "User with email: user@mail.com not found")
    private T message;

    @Schema(description = "Path to the end point",
            example = "/user/registration/dXNlckBtYWlsLmNvbQ%3D%3D")
    private String path;

}