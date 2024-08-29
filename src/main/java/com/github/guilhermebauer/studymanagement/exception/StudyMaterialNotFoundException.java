package com.github.guilhermebauer.studymanagement.exception;


import com.github.guilhermebauer.studymanagement.enums.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudyMaterialNotFoundException extends RuntimeException {

    public static final ExceptionDetails ERROR = ExceptionDetails.STUDY_MATERIAL_NOT_FOUND_MESSAGE;

    public StudyMaterialNotFoundException(String message) {
        super(ERROR.formatErrorMessage(message));
    }
}