package com.github.guilhermebauer.studymanagement.exception;

import com.github.guilhermebauer.studymanagement.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationUtilsException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.EXCEPTION_TYPE_NOT_THROWN;

    public ValidationUtilsException(String message, Throwable cause) {
        super(ERROR.formatErrorMessage(message), cause);
    }
}
