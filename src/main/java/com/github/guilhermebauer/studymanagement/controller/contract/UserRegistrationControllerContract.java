package com.github.guilhermebauer.studymanagement.controller.contract;

import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.model.values.UserVO;
import com.github.guilhermebauer.studymanagement.response.UserRegistrationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.github.guilhermebauer.studymanagement.service.UserRegistrationService;

/**
 * Contract for user registration operations in the application.
 *
 * <p>This controller handles HTTP requests related to user registration.
 * It defines the endpoint for creating new users based on the provided user information.
 */


public interface UserRegistrationControllerContract {

    /**
     * Registers a new user with the provided user details.
     *
     * <p>This method consumes a {@link UserVO} object in JSON format, processes the user
     * registration request, and returns a response containing the registered user's information.
     *
     * @param userVO The {@link UserVO} object containing details of the user to be registered,
     *               such as name, email, and password.
     * @return {@link ResponseEntity} containing {@link UserRegistrationResponse} with
     *         details of the registered user and appropriate HTTP status.
     * @see UserRegistrationResponse
     * @see UserVO
     * @see UserRegistrationService
     */

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Register a new User",
            description = "Creates a new User and returns the created user object.",
            tags = "user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = UserRegistrationService.class))),
            @ApiResponse(responseCode = "400", description = "Email All Ready Registered Exception when the email is duplicated.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User Not Found or Field Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    ResponseEntity<UserRegistrationResponse> createUser(@RequestBody UserVO userVO);
}
