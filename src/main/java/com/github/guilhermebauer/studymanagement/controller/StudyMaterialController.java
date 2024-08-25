package com.github.guilhermebauer.studymanagement.controller;

import com.github.guilhermebauer.studymanagement.controller.contract.StudyMaterialControllerContract;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.service.StudyMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/studyMaterial")
public class StudyMaterialController implements StudyMaterialControllerContract {

    private final StudyMaterialService service;

    @Autowired
    public StudyMaterialController(StudyMaterialService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<StudyMaterialVO> create(StudyMaterialVO studyMaterialVO) throws IllegalAccessException, NoSuchFieldException {
        StudyMaterialVO createdStudyMaterialVO = service.create(studyMaterialVO);
        return new ResponseEntity<>(createdStudyMaterialVO, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<StudyMaterialVO> update(StudyMaterialVO studyMaterialVO) throws NoSuchFieldException, IllegalAccessException {
        return null;
    }

    @Override
    public ResponseEntity<StudyMaterialVO> findById(String id) throws NoSuchFieldException, IllegalAccessException {
        StudyMaterialVO studyMaterialByID = service.findByID(id);
        return ResponseEntity.ok(studyMaterialByID);
    }

    @Override
    public ResponseEntity<StudyMaterialVO> findAll(StudyMaterialVO studyMaterialVO) throws NoSuchFieldException, IllegalAccessException {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(String id) {

        service.delete(id);
        return ResponseEntity.noContent().build();


    }

    @Override
    public ResponseEntity<StudyMaterialVO> addLinkInStudyMaterial(LinkListToStudyMaterialRequest request) throws IllegalAccessException, NoSuchFieldException {
        StudyMaterialVO studyMaterialVO = service.addLinkInStudyMaterial(request);
        return new ResponseEntity<>(studyMaterialVO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<StudyMaterialVO> updateLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request) throws NoSuchFieldException, IllegalAccessException {
        StudyMaterialVO updatedLink = service.updateLinkInStudyMaterial(request);
        return ResponseEntity.ok(updatedLink);
    }

    @Override
    public ResponseEntity<Page<LinkVO>> findAllLinksInStudyMaterial(String studyMaterialId, Pageable pageable) {
        Page<LinkVO> allLinksInStudyMaterial = service.findAllLinksInStudyMaterial(studyMaterialId, pageable);
        return ResponseEntity.ok(allLinksInStudyMaterial);
    }

    @Override
    public ResponseEntity<StudyMaterialVO> deleteLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request) throws NoSuchFieldException, IllegalAccessException {
        StudyMaterialVO studyMaterialVO = service.deleteLinkInStudyMaterial(request);
        return ResponseEntity.ok(studyMaterialVO);
    }


}
