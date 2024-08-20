package com.github.guilhermebauer.studymanagement.exception;

import java.util.Date;

public record ExceptionResponse(String message, String details, Date timeStamp) {


}
