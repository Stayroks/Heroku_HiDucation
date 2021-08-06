package com.telran.hiducation.pojo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AppRootDto {

    ProductInfoDto appInfo;

    ProductProgressDto appProgress;

}
