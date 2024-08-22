package com.github.guilhermebauer.studymanagement.factory;

import com.github.guilhermebauer.studymanagement.model.CourseEntity;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import com.github.guilhermebauer.studymanagement.utils.UuidUtils;

import java.util.List;

public class StudyMaterialFactory {

    public StudyMaterialFactory() {
    }

    public static StudyMaterialEntity create(String title, String content,
                                             CourseEntity courseEntity, List<LinkEntity> links){
        return new StudyMaterialEntity(UuidUtils.generateUuid(),title,content,courseEntity, links);
    }

}
