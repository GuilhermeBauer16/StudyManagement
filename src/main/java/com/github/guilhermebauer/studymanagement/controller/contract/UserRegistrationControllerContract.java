package com.github.guilhermebauer.studymanagement.controller.contract;

import com.github.guilhermebauer.studymanagement.model.values.UserVO;
import com.github.guilhermebauer.studymanagement.response.UserRegistrationResponse;
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
    ResponseEntity<UserRegistrationResponse> createUser(@RequestBody UserVO userVO);
}
