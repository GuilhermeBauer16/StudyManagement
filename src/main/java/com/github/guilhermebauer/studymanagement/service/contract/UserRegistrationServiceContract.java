package com.github.guilhermebauer.studymanagement.service.contract;

import com.github.guilhermebauer.studymanagement.model.values.UserVO;
import com.github.guilhermebauer.studymanagement.response.UserRegistrationResponse;

public interface UserRegistrationServiceContract {

    UserRegistrationResponse createUser(UserVO userVO);
}
