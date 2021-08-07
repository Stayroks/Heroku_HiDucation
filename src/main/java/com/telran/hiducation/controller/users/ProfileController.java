package com.telran.hiducation.controller.users;

import com.telran.hiducation.pojo.dto.ErrorDto;
import com.telran.hiducation.pojo.dto.ResponseSuccessDto;
import com.telran.hiducation.pojo.dto.UserProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Tag(name = "Users")
@RequestMapping("${endpoint.url.user.controller}")
public interface ProfileController {

    @GetMapping(value = "{userEmail}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get user profile",
            description = "Get all user data")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Displays user data",
                            content = @Content(
                                    schema = @Schema(implementation = UserProfileDto.class)
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User with this email is not registered",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity getUserProfile(Principal principal);

    @PutMapping(value = "{userEmail}", produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update user profile",
            description = "Refresh user data"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Data update was successful",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseSuccessDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode =  "401",
                            description = " “Unauthorized” response returned for requests with missing or incorrect credentials",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User with this email is not registered",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorDto.class)
                            )
                    )
            }
    )
    ResponseEntity updateProfile(Principal principal, @RequestBody UserProfileDto userDto);


//    @DeleteMapping("{userEmail}")
//    void delete(@PathVariable String userEmail);


}

