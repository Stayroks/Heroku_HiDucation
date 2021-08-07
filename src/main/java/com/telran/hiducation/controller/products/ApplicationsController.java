package com.telran.hiducation.controller.products;

import com.telran.hiducation.pojo.dto.AppRootDto;
import com.telran.hiducation.pojo.dto.ErrorDto;
import com.telran.hiducation.pojo.dto.ProductInfoDto;
import com.telran.hiducation.pojo.dto.ResponseSuccessDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Applications", description = "All products (applications) hiMathGaming")
@RequestMapping("${endpoint.url.applications.controller}")
public interface ApplicationsController {


//    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PostMapping()
    @Operation(summary = "Add a new application")
    @ApiResponses({
            @ApiResponse(
                    responseCode =  "201",
                    description = "Application has been successfully added to the database",
                    content = @Content(
                            schema = @Schema(implementation = ResponseSuccessDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode =  "400",
                    description = "<b>Invalid email or password format:</b>" +
                            "</br>Email must contain one @ symbol and at least 2 characters after the last dot." +
                            "</br>Password must contain at least 8 characters",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode =  "409",
                    description = "This application is already in the database",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class)
                    )
            )
    })
    ResponseEntity<?> addApplication(@RequestBody  AppRootDto dto);

    @PostMapping("{appName}")
    @Operation(
            summary = "Add application to user",
            description = "Add an application to the user by the name of the application"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Returns a message that the user was successfully added to the list of installed applications",
                    content = @Content(
                            schema = @Schema(implementation = ResponseSuccessDto.class)
                    )
            )
    })
    ResponseEntity<?> addAppToUserByAppName(Principal principal, @PathVariable String appName);

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get all the applications",
            description = "Get all the applications of the company"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode =  "200",
                    description = "Returns a list of all active company applications",
                    content = @Content(
                            schema = @Schema(implementation = ResponseSuccessDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode =  "400",
                    description = "<b>Invalid email or password format:</b>" +
                            "</br>Email must contain one @ symbol and at least 2 characters after the last dot." +
                            "</br>Password must contain at least 8 characters",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode =  "401",
                    description = " “Unauthorized” response returned for requests with missing or incorrect credentials",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class)
                    )
            )
    })
    ResponseEntity<List<ProductInfoDto>> getAllProducts();
}
