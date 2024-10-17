package com.github.guilhermebauer.studymanagement.service.contract;

import com.github.guilhermebauer.studymanagement.request.LoginRequest;
import com.github.guilhermebauer.studymanagement.response.LoginResponse;

public interface LoginServiceContract {

    LoginResponse login(LoginRequest loginRequest);
}
