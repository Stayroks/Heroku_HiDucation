package com.telran.hiducation.controller.products;

import com.telran.hiducation.pojo.dto.AppRootDto;
import com.telran.hiducation.pojo.dto.ProductInfoDto;
import com.telran.hiducation.service.apps.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@Validated
public class ApplicationsControllerImpl implements ApplicationsController {

    private final ApplicationService applicationService;

    @Override
    public ResponseEntity<?> addApplication(AppRootDto dto) {
        return new ResponseEntity<>(applicationService.addApp(dto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> addAppToUserByAppName(Principal principal, String appName) {
        return ResponseEntity.ok(applicationService.addApplicationToUserByApplicationName(principal, appName));
    }

    @Override
    public ResponseEntity<List<ProductInfoDto>> getAllProducts() {
        return ResponseEntity.ok(applicationService.getAllApps());
    }


}
