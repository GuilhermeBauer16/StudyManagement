package com.github.guilhermebauer.studymanagement.factory;

import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import com.github.guilhermebauer.studymanagement.utils.UuidUtils;

import java.util.List;

public class LinkEntityFactory {

    public LinkEntityFactory() {
    }

    public static LinkEntity create(String url,String description){
        return new LinkEntity(UuidUtils.generateUuid(),url,description);
    }



}
