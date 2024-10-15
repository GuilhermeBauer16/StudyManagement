package com.github.guilhermebauer.studymanagement.controller;

import com.github.guilhermebauer.studymanagement.controller.contract.UserRegistrationControllerContract;
import com.github.guilhermebauer.studymanagement.model.values.UserVO;
import com.github.guilhermebauer.studymanagement.response.UserRegistrationResponse;
import com.github.guilhermebauer.studymanagement.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/signUp")
public class UserRegistrationController implements UserRegistrationControllerContract {


    private final UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;

    }

    @Override
    public ResponseEntity<UserRegistrationResponse> createRole(UserVO userVO) {
        UserRegistrationResponse userCreated = userRegistrationService.createUser(userVO);

        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }
}
