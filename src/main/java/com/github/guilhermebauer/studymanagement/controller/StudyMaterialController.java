package com.github.guilhermebauer.studymanagement.controller;

import com.github.guilhermebauer.studymanagement.controller.contract.StudyMaterialControllerContract;
import com.github.guilhermebauer.studymanagement.model.values.LinkVO;
import com.github.guilhermebauer.studymanagement.model.values.StudyMaterialVO;
import com.github.guilhermebauer.studymanagement.request.LinkListToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.SingleLinkToStudyMaterialRequest;
import com.github.guilhermebauer.studymanagement.request.StudyMaterialUpdateRequest;
import com.github.guilhermebauer.studymanagement.response.StudyMaterialResponse;
import com.github.guilhermebauer.studymanagement.response.StudyMaterialUpdateResponse;
import com.github.guilhermebauer.studymanagement.service.StudyMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studyMaterial")
public class StudyMaterialController implements StudyMaterialControllerContract {

    private final StudyMaterialService service;

    @Autowired
    public StudyMaterialController(StudyMaterialService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<StudyMaterialResponse> create(StudyMaterialVO studyMaterialVO) {
        StudyMaterialResponse createdStudyMaterialVO = service.create(studyMaterialVO);
        return new ResponseEntity<>(createdStudyMaterialVO, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<StudyMaterialUpdateResponse> update(StudyMaterialUpdateRequest request) {
        StudyMaterialUpdateResponse updatedStudyMaterialVO = service.update(request);
        return ResponseEntity.ok(updatedStudyMaterialVO);
    }

    @Override
    public ResponseEntity<StudyMaterialResponse> findById(String id) {
        StudyMaterialResponse studyMaterialByID = service.findByID(id);
        return ResponseEntity.ok(studyMaterialByID);
    }

    @Override
    public ResponseEntity<Page<StudyMaterialResponse>> findAll(Pageable pageable) {
        Page<StudyMaterialResponse> allStudyMaterials = service.findAll(pageable);
        return ResponseEntity.ok(allStudyMaterials);
    }

    @Override
    public ResponseEntity<Void> delete(String id) {

        service.delete(id);
        return ResponseEntity.noContent().build();


    }

    @Override
    public ResponseEntity<StudyMaterialResponse> addLinkInStudyMaterial(LinkListToStudyMaterialRequest request) {
        StudyMaterialResponse studyMaterialVO = service.addLinkInStudyMaterial(request);
        return new ResponseEntity<>(studyMaterialVO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<StudyMaterialResponse> updateLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request){
        StudyMaterialResponse updatedLink = service.updateLinkInStudyMaterial(request);
        return ResponseEntity.ok(updatedLink);
    }

    @Override
    public ResponseEntity<Page<LinkVO>> findAllLinksInStudyMaterial(String studyMaterialId, Pageable pageable) {
        Page<LinkVO> allLinksInStudyMaterial = service.findAllLinksInStudyMaterial(studyMaterialId, pageable);
        return ResponseEntity.ok(allLinksInStudyMaterial);
    }

    @Override
    public ResponseEntity<Void> deleteLinkInStudyMaterial(SingleLinkToStudyMaterialRequest request) {
        service.deleteLinkInStudyMaterial(request);
        return ResponseEntity.noContent().build();
    }


}
