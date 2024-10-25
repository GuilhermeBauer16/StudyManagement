package com.github.guilhermebauer.studymanagement.controller.contract;

import com.github.guilhermebauer.studymanagement.request.LoginRequest;
import com.github.guilhermebauer.studymanagement.response.LoginResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.github.guilhermebauer.studymanagement.service.LoginService;

/**
 * API endpoint contract for user login operations.
 *
 * <p>This controller provides an endpoint for authenticating users.
 * The client must send a {@link LoginRequest} containing user credentials,
 * and if successful, receives a {@link LoginResponse} containing the authentication details
 * including a JWT token.
 */

public interface LoginControllerContract {

    /**
     * Authenticates a user based on provided login credentials.
     *
     * <p>This endpoint accepts a JSON payload containing the user's credentials,
     * such as username/email and password. On successful authentication,
     * it returns a {@link LoginResponse} with a JWT token.
     *
     * @param request The {@link LoginRequest} object containing the user's login credentials.
     * @return {@link ResponseEntity} containing the {@link LoginResponse} with a JWT token
     *         if authentication is successful, or an appropriate error response otherwise.
     * @see LoginResponse
     * @see LoginRequest
     * @see LoginService
     */

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);
}
