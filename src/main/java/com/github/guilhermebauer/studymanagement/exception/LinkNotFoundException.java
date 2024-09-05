package com.github.guilhermebauer.studymanagement.exception;


import com.github.guilhermebauer.studymanagement.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LinkNotFoundException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.LINK_NOT_FOUND_MESSAGE;

    public LinkNotFoundException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}
