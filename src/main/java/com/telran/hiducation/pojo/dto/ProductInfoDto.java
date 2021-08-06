package com.telran.hiducation.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductInfoDto {

    @Schema(description = "Application name", example = "integral-basics")
    public String appName;

    @Schema(description = "Display name", example = "Integral Basics")
    public String displayName;

    @Schema(description = "Academic discipline", example = "Math")
    public String field;

}
