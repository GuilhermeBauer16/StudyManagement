package com.github.guilhermebauer.studymanagement.factory;

import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.utils.UuidUtils;


public class CourseFactory {

    public CourseFactory() {
    }

    public static CourseEntity create(String title, String description){
        return new CourseEntity(UuidUtils.generateUuid(),title,description);
    }
}
