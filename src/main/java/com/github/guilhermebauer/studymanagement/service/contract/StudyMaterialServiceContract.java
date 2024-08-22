package com.github.guilhermebauer.studymanagement.service.contract;

import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;

import java.util.List;

public interface StudyMaterialServiceContract {

    StudyMaterialEntity create(StudyMaterialEntity studyMaterialEntity);
}
