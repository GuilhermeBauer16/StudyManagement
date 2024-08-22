package com.github.guilhermebauer.studymanagement.controller;

import com.github.guilhermebauer.studymanagement.controller.contract.LinkControllerContract;
import com.github.guilhermebauer.studymanagement.model.LinkEntity;
import com.github.guilhermebauer.studymanagement.model.StudyMaterialEntity;
import com.github.guilhermebauer.studymanagement.request.StudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.service.StudyMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController implements LinkControllerContract {
    @Autowired
    private StudyMaterialService service;

    @Override
    public ResponseEntity<StudyMaterialEntity> create(StudyMaterialEntity studyMaterialEntity) {
        StudyMaterialEntity studyMaterialEntity1 = service.create(studyMaterialEntity);
        return new ResponseEntity<>(studyMaterialEntity1, HttpStatus.CREATED);

    }

}
