package com.telran.hiducation.service.apps;

import com.telran.hiducation.pojo.dto.AppRootDto;
import com.telran.hiducation.pojo.dto.ProductInfoDto;
import com.telran.hiducation.pojo.dto.ResponseSuccessDto;

import java.security.Principal;
import java.util.List;

public interface ApplicationService {

    ResponseSuccessDto addApp(AppRootDto dto);

    ResponseSuccessDto addApplicationToUserByApplicationName(Principal principal, String appName);

    List<ProductInfoDto> getAllApps();
}
