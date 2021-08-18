package com.telran.hiducation.controller.users;

import com.telran.hiducation.pojo.dto.ErrorDto;
import com.telran.hiducation.pojo.dto.PasswordResetDto;
import com.telran.hiducation.pojo.dto.ResponseSuccessDto;
import com.telran.hiducation.pojo.dto.UserCredentialsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin("*")
@Tag(name = "Users", description = "Operations with users")
@RequestMapping("${endpoint.url.user.controller}")
@Validated
public interface AuthController {

    @PostMapping(value = "${endpoint.url.user.registration}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Creates a new user account",
            description = "Provided email address must be unique. After successful registration a confirmation email must be sent to provided address. Confirmation email must contain confirmation link with key that will be used in API call to initiate account."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode =  "201",
                    description = "Returns the email address to which the confirmation request was sent",
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
                    description = "The user with this email already exists",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDto.class)
                    )
            )
    })
    ResponseEntity registration(@RequestBody @Valid UserCredentialsDto body);

    @RequestMapping(value = "${endpoint.url.user.registration}/{hash}")
//    @GetMapping(value = "${endpoint.url.user.registration}/{hash}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @Operation(summary = "Activate user account",
            description = "Marks user account is activated")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Registration completed successfully. Redirecting the user to the login page",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseSuccessDto.class)
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
//    ResponseEntity confirmRegistration(@PathVariable @Parameter(description = "User's encoded email") String hash);
    String confirmRegistration(@PathVariable @Parameter(description = "User's encoded email") @RequestParam(name = "hash", required = false, defaultValue = "index") String hash);

    @PostMapping("${endpoint.url.user.login}")
    @Operation(
            summary = "Login and retrieve an user token",
            description = "Login and retrieve an user token"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode =  "200",
                    description = "The authorization field in the HTTP header returns a token for passing the user's credentials",
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
    void userLogin(@RequestBody @Valid UserCredentialsDto credentialsDto);

    @PutMapping(value = "{userEmail}/${endpoint.url.password.reset}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    @Operation(summary = "Change user password", description = "Updating password when user fill form with new password")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Password has been successfully changed",
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
    ResponseEntity resetPassword(Principal principal, @RequestBody PasswordResetDto passwordResetDto);

}
