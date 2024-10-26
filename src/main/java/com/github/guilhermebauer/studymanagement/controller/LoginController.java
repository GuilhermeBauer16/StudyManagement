package com.github.guilhermebauer.studymanagement.controller;

import com.github.guilhermebauer.studymanagement.controller.contract.LoginControllerContract;
import com.github.guilhermebauer.studymanagement.request.LoginRequest;
import com.github.guilhermebauer.studymanagement.response.LoginResponse;
import com.github.guilhermebauer.studymanagement.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController implements LoginControllerContract {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        LoginResponse login = loginService.login(request);
        return ResponseEntity.ok(login);
    }
}
