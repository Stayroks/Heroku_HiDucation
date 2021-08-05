package com.telran.hiducation.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Schema(name = "Response success", description = "The request has succeeded")
public class ResponseSuccessDto {
    @Schema(
            description = "The information returned with the response is dependent on the method used in the request",
            example = "john@gmail.com"
    )
    String message;

}
