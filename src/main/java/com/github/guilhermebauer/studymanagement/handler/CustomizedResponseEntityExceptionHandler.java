package com.github.guilhermebauer.studymanagement.handler;


import com.github.guilhermebauer.studymanagement.exception.CourseNotFoundException;
import com.github.guilhermebauer.studymanagement.exception.ExceptionResponse;
import com.github.guilhermebauer.studymanagement.exception.FieldNotFound;
import com.github.guilhermebauer.studymanagement.exception.LinkNotFoundException;
import com.github.guilhermebauer.studymanagement.exception.RoleAllReadyRegisterException;
import com.github.guilhermebauer.studymanagement.exception.RoleNotFoundException;
import com.github.guilhermebauer.studymanagement.exception.StudyMaterialNotFoundException;
import com.github.guilhermebauer.studymanagement.exception.UuidUtilsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler {

    @ExceptionHandler({
            FieldNotFound.class,
            CourseNotFoundException.class,
            StudyMaterialNotFoundException.class,
            LinkNotFoundException.class,
            RoleNotFoundException.class,

    })
    public final ResponseEntity<ExceptionResponse> handlerNotFoundException(
            Exception ex,
            WebRequest webRequest
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                webRequest.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({UuidUtilsException.class,
    RoleAllReadyRegisterException.class})
    public final ResponseEntity<ExceptionResponse> handlerInternalServerErrorException(
            Exception ex,
            WebRequest webRequest
    ) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                webRequest.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

}
