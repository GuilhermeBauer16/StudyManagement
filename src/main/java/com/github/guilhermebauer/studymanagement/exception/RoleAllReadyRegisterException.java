package com.github.guilhermebauer.studymanagement.exception;


import com.github.guilhermebauer.studymanagement.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleAllReadyRegisterException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.ROLE_ALREADY_REGISTER_MESSAGE;

    public RoleAllReadyRegisterException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
