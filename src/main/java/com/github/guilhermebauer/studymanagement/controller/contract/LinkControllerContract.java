package com.github.guilhermebauer.studymanagement.controller.contract;

import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import com.github.guilhermebauer.studymanagement.request.StudyMaterialRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface LinkControllerContract {

    @PostMapping
    ResponseEntity<StudyMaterialEntity> create(@RequestBody StudyMaterialEntity studyMaterialEntity);
}
